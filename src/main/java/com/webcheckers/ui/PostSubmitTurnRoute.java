package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.application.ReplayManager;
import com.webcheckers.model.*;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.*;

import static com.webcheckers.util.Message.error;
import static com.webcheckers.util.Message.info;
import static spark.Spark.halt;

/**
 * Submits the user turn. This is only active when the game view is in a
 * Stable Turn state.
 * The response body must be a message that has INFO type if the turn as a
 * whole is valid and teh server has processed the turn. It refreshes the
 * page using a 'GET /game' URL.
 *
 * @author Austin Miller 'akm8654'
 *
 *
 * Added functionality for King
 * @author Mario Castano 'mac3186'
 * @author Mikayla Wishart 'mcw7246'
 */
public class PostSubmitTurnRoute implements Route
{
  private ReplayManager rManager;

  public PostSubmitTurnRoute(ReplayManager rManager)
  {
    this.rManager = rManager;
  }

  @Override
  public Object handle(Request request, Response response)
  {
    boolean madeJump = false;
    final Session session = request.session();
    final Player player = session.attribute(GetHomeRoute.PLAYER_KEY);
    final Gson gson = new Gson();
    GameManager manager = session.attribute(GetHomeRoute.GAME_MANAGER_KEY);
    if (player != null)
    {
      int gameID = manager.getGameID(player.getUsername());
      CheckerGame game = manager.getLocalGame(player.getUsername());
      if (game == null)
      {
        game = manager.getGame(gameID);
        if (game == null)
        {
          response.redirect(WebServer.HOME_URL);
          return "Redirected Home";
        }
      }

      Piece.Color color = Piece.Color.WHITE;
      Piece.Color oppColor = Piece.Color.RED;
      if (player.getPlayerNum() == 1)
      {
        color = Piece.Color.RED;
        oppColor = Piece.Color.WHITE;
      }

      //Once moves are validated, king any pieces that made it to the edge of the board
      final ArrayList<Move> moves = session.attribute(PostValidateMoveRoute.MOVE_LIST_ID);
      final Position lastPos = moves.get(moves.size() -1).getEnd();
      final int lastMoveColumn = lastPos.getCell();
      if(
        //lastMove.getEnd().getRow() == 0
          lastPos.equals(new Position(0, lastMoveColumn))
          && game.getColor() == Piece.Color.RED)
      {
        game.getBoard().kingPieceAt(lastPos);
      }
      else if(
        lastPos.equals(new Position(7, lastMoveColumn))
        && game.getColor() == Piece.Color.WHITE)
      {
        game.getBoard().kingPieceAt(lastPos);
      }
      //Once all pieces are made kings, and all moves made are validated, update the server (GameManager) copy
      //Board board = game.getBoard();

      /*
       * see if the person made a jump
       * get the space they jumped from
       *
       */
      Space jumpSpace;
      while((jumpSpace = game.getJumpedPiece()) != null)
      {
        jumpSpace.setPiece(null);
        madeJump = true;
      }



      //made a jump that was a valid jump
      if(madeJump)
      {
        RequireMove requireMove = new RequireMove(game.getBoard(), color);
        //gets all the jumps that are valid for the given board
        List<Move> listMoves = session.attribute(PostValidateMoveRoute.MOVE_LIST_ID);
        Space jumpEndSpace;
        int listSize = listMoves.size();
        Move lastMove = listMoves.get(listSize - 1);
        jumpEndSpace = game.getBoard().getSpaceAt(lastMove.getEnd().getRow(),
               lastMove.getEnd().getCell());

        //can only go in one direction (the player is a single piece)
        if (jumpEndSpace.getPiece().getType() != Piece.Type.KING)
        {
          //if there is still valid moves that are jumps
          if (requireMove.getValidMoves(game.getBoard(), jumpEndSpace, color) != null)
          {
            //a list of all the valid moves
            Stack<Move> validMoves = new Stack<>();
            int colorFactor = 1;
            if (color.equals(Piece.Color.WHITE))
            {
              colorFactor = -1;
            }
            validMoves = requireMove.addMovesRowOneDirection(validMoves,
                    game.getBoard(), jumpEndSpace.getPiece(), jumpEndSpace, colorFactor);
            Move availableMove;
            while(true)
            {
              if (validMoves.isEmpty())
              {
                break;
              } else if(!validMoves.get(0).getStatus().equals(Move.MoveStatus.VALID))
              {
                break;
              } else
              {
                validMoves.pop();
              }
            }
            if (validMoves.size() != 0)
            {
              return gson.toJson(error("There is still an available jump. You" +
                      " must make this move before you end your turn."));
            }
          }
        }

        //if it is a king
        else
        {
          if (requireMove.getValidMoves(game.getBoard(), jumpEndSpace, session.attribute(GetGameRoute.ACTIVE_COLOR)) != null)
          {
            Stack<Move> validMoves = requireMove.getValidMoves(game.getBoard(), jumpEndSpace, session.attribute(GetGameRoute.ACTIVE_COLOR));
            //go through the stack of valid moves for the given space
            do
            {
              //loops through to see if there is another valid jump that can me made
              //if so then it will return a message saying it is necessary
              if (validMoves.pop().getStatus() == Move.MoveStatus.JUMP)
              {
                return gson.toJson(error("There is still an available jump. " +
                        "You must make this move before you end your turn."));
              }
            }
            while (!validMoves.empty());
          }
          //stores row then cols.
          Map<Integer, Integer> startPos = new HashMap<>();
          for (Move move : moves)
          {
            Position start = move.getStart();
            Position end = move.getEnd();
            try
            {
              int cell = startPos.get(end.getRow());
              if(cell == end.getCell())
              {
                return gson.toJson(error("You doubled back on yourself. That is" +
                        " corrupt monarchy! And that is not allowed here!"));
              } else
              {
                startPos.put(start.getRow(), start.getCell());
              }
            } catch (NullPointerException e)
            {
              startPos.put(start.getRow(), start.getCell());
            }
          }
        }
        //Jump not made
      } else {
        CheckerGame originalGame = manager.getGame(gameID);
        RequireMove requireMove = new RequireMove(originalGame.getBoard(), color);
        Map<Move.MoveStatus, List<Move>> validMoves = requireMove.getAllMoves();
        List<Move> jumps = validMoves.get(Move.MoveStatus.JUMP);
        if (jumps != null && !jumps.isEmpty())
        {
          return gson.toJson(error("There is still an available jump. " +
                  "You must make this move before you end your turn."));
        }
      }

      manager.updateGame(gameID, game);
      manager.removeClientSideGame(player.getUsername());
      game.updateTurn();
      rManager.addMove(gameID, game.getBoard());
      return gson.toJson(info("Valid Move"));
    } else
    {
      response.redirect(WebServer.HOME_URL);
    }
    return "Redirected Home";
  }

}
