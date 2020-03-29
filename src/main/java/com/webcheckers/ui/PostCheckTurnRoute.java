package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import static spark.Spark.halt;

public class PostCheckTurnRoute implements Route
{


  @Override
  public Object handle(Request request, Response response) throws Exception
  {
    final Session httpSession = request.session();
    final PlayerLobby lobby = httpSession.attribute(GetHomeRoute.PLAYER_LOBBY_KEY);
    final Player player = httpSession.attribute(GetHomeRoute.PLAYER_KEY);

    if(lobby != null)
    {
      final CheckerGame game = lobby.getGame(player.getUsername());
      if (game == null)
      {
        response.redirect(WebServer.HOME_URL);
        halt();
        return null;
      }

    } else
    {
      response.redirect(WebServer.HOME_URL);
      halt();
    }
    return null;
  }
}
