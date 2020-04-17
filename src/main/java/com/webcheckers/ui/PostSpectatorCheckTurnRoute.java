package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import static com.webcheckers.util.Message.info;
import static spark.Spark.halt;

public class PostSpectatorCheckTurnRoute implements Route
{

  @Override
  public Object handle(Request request, Response response)
  {
    final Session httpSession = request.session();
    final Player spectator = httpSession.attribute(GetHomeRoute.PLAYER_KEY);
    final Gson gson = new Gson();
    if (spectator != null)
    {
      GameManager manager = httpSession.attribute(GetHomeRoute.GAME_MANAGER_KEY);
      String username = spectator.getUsername();
      int gameID = manager.getGameID(username);
      final CheckerGame game = manager.getGame(gameID);
      String turn = httpSession.attribute(GetSpectatorGameRoute.TURN);
      if (game.getTurn().equals(turn))
      {
        return gson.toJson(info("false"));
      } else
      {
        return gson.toJson(info("true"));
      }
    } else
    {
      response.redirect(WebServer.HOME_URL);
      return "Redirected Home";
    }
  }
}
