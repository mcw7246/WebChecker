package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.*;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

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
  public Object handle(Request request, Response response){
    final Session session = request.session();
    final Player player = session.attribute(GetHomeRoute.PLAYER_KEY);
    final Gson gson = new Gson();
    GameManager manager = session.attribute(GetHomeRoute.GAME_MANAGER_KEY);
    if(player != null)
    {
      int gameID = manager.getGameID(player.getUsername());
      final CheckerGame game = manager.getGame(gameID);
      Board board = session.attribute(GetGameRoute.GAME_BOARD);
      //TODO: GAME LOGIC (were the moves that were made correct)
      game.updateBoard(board);
      manager.updateGame(gameID, game);
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
