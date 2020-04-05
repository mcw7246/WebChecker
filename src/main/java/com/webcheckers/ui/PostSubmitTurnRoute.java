package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.model.*;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.ArrayList;
import java.util.List;

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
 */
public class PostSubmitTurnRoute implements Route
{

  @Override
  public Object handle(Request request, Response response)
  {
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
          halt();
          return "Redirected Home";
        }
      }
      //TODO: GAME LOGIC (were the moves that were made correct)
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
      manager.updateGame(gameID, game);
      manager.removeClientSideGame(player.getUsername());
      game.updateTurn();
      return gson.toJson(info("valid move"));
    } else
    {
      response.redirect(WebServer.HOME_URL);
      halt();
      return "Redirected Home";
    }
  }

}
