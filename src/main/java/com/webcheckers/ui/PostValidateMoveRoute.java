package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import spark.*;

import java.util.Objects;

import static com.webcheckers.ui.PostRequestGameRoute.MESSAGE;
import static spark.Spark.halt;

/**
 * This action submits a single move for validation. The server must keep
 * track of each proposed move for a single turn in the user's game state. It
 * sends INFO if the move is valid and an ERROR if not. The text should say
 * why the move is invalid.
 *
 * Query's Params for actionData Move (As a class).
 *
 * @author Austin Miller 'akm8654'
 */
public class PostValidateMoveRoute implements Route
{
  /** All the types of moves */
  public enum MoveStatus {INVALID_SPACE, VALID, OCCUPIED, TOO_FAR, SAME_SPACE
    , INVALID_BACKWARDS, JUMP_OWN, INVALID_DIR}

  Gson gson = new Gson();
  private static final String ACTION_DATA = "actionData";

  private final TemplateEngine templateEngine;

  PostValidateMoveRoute(final TemplateEngine templateEngine)
  {
    Objects.requireNonNull(templateEngine, "templateEngine must not be" +
            "null");
    this.templateEngine = templateEngine;
  }

  /**
   * Helper method for setting the error message on teh game screen
   *
   * @param session: the HTTP session
   * @param message: the message to be sent.
   */
  private void setErrorMsg(Session session, String message)
  {
    session.attribute(GetHomeRoute.ERROR_MESSAGE_KEY, message);
  }

  /**
   * Helper method for setting the message on the game screen when a move
   * is valid.
   *
   * @param session the HTTP session
   * @param message the message to be sent.
   */
  private void setMsg(Session session, String message)
  {
    session.attribute(MESSAGE, message);
  }

  @Override
  public String handle(Request request, Response response)
  {
    final Session httpSession = request.session();
    final String moveStr = request.queryParams(ACTION_DATA);
    final Move move = gson.fromJson(moveStr, Move.class);
    final PlayerLobby lobby = httpSession.attribute(GetHomeRoute.PLAYER_LOBBY_KEY);
    final Player player = httpSession.attribute(GetHomeRoute.PLAYER_KEY);
    final CheckerGame game = lobby.getGame(player.getUsername());

    if (lobby != null && game != null)
    {
      MoveStatus moveValidity = move.validateMove(game);
      String msg;
      switch (moveValidity)
      {
        case INVALID_SPACE:
          msg = "Invalid Move: Space is the wrong color!";
          setErrorMsg(httpSession, msg);
          break;
        case VALID:
          break;
        case OCCUPIED:
          msg = "Invalid Move: Space is already Occupied!";
          setErrorMsg(httpSession, msg);
          break;
        case TOO_FAR:
          msg = "Invalid Move: Space is too far away!";
          setErrorMsg(httpSession, msg);
          break;
        case SAME_SPACE:
          msg = "Invalid Move: The Space you've tried to move to is the same " +
                  "one!";
          setErrorMsg(httpSession, msg);
          break;
        case INVALID_BACKWARDS:
          msg = "Invalid Move: You aren't kinged yet! You must move forward!";
          setErrorMsg(httpSession, msg);
          break;
        case JUMP_OWN:
          msg = "Invalid Move: You're attempting to jump your own piece!";
          setErrorMsg(httpSession, msg);
          break;
        case INVALID_DIR:
          msg = "Invalid Move: You must move diagonally!";
          setErrorMsg(httpSession, msg);
          break;
      }

      response.redirect(WebServer.GAME_URL);
    } else
    {
      response.redirect(WebServer.HOME_URL);
      halt();
    }
    return null;

  }
}
