package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

import static com.webcheckers.util.Message.info;
import static spark.Spark.halt;

public class PostCheckTurnRoute implements Route
{

  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  @Override
  public Object handle(Request request, Response response)
  {
    final Session httpSession = request.session();
    final Player player = httpSession.attribute(GetHomeRoute.PLAYER_KEY);
    final Gson gson = new Gson();
    GameManager manager = httpSession.attribute(GetHomeRoute.GAME_MANAGER_KEY);
    if (player != null)
    {
      int gameID = manager.getGameID(player.getUsername());
      final CheckerGame game = manager.getGame(gameID);
      if (game.getTurn().equals(player.getUsername()))
      {
        System.out.println("Turn: true");
        return gson.toJson(info("true"));
      } else
      {
        return gson.toJson(info("false"));
      }
    } else
    {
      response.redirect(WebServer.HOME_URL);
      halt();
      return "Redirected Home";
    }
  }
}
