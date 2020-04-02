package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.webcheckers.util.Message.info;
import static spark.Spark.halt;

public class PostCheckTurnRoute implements Route
{

  private PlayerLobby lobby;

  public PostCheckTurnRoute(){
  }

  @Override
  public Object handle(Request request, Response response) throws Exception
  {
    final Session httpSession = request.session();
    final Player player = httpSession.attribute(GetHomeRoute.PLAYER_KEY);
    final Gson gson = new Gson();
    this.lobby = httpSession.attribute(GetHomeRoute.PLAYER_LOBBY_KEY);
    GameManager manager = httpSession.attribute(GetHomeRoute.GAME_MANAGER_KEY);
    if(player != null)
    {
      int gameID = manager.getGameID(player.getUsername());
      final CheckerGame game = manager.getGame(gameID);
      if (game.getTurn().equals(player.getUsername()))
      {
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
