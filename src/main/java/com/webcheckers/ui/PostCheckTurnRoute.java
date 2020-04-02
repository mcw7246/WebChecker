package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
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

  private final GameManager gameManager;
  private final PlayerLobby playerLobby;



  public PostCheckTurnRoute(final PlayerLobby playerLobby, final GameManager gameManager){
    this.gameManager = gameManager;
    this.playerLobby = playerLobby;
  }

  @Override
  public Object handle(Request request, Response response) throws Exception
  {
    final Session httpSession = request.session();
    final Player player = httpSession.attribute(GetHomeRoute.PLAYER_KEY);
    if(this.playerLobby != null)
    {
      int gameID = this.gameManager.getGameID(player.getUsername());
      final CheckerGame game = this.gameManager.getGame(gameID);
      if (game == null)
      {
        response.redirect(WebServer.HOME_URL);
        halt();
        return null;
      } else
      {

      }
    } else
    {
      response.redirect(WebServer.HOME_URL);
      halt();
    }
    return null;
  }
}
