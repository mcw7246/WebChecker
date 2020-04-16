package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.model.Player;
import javafx.geometry.Pos;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static com.webcheckers.ui.GetHomeRoute.*;
import static com.webcheckers.ui.WebServer.HOME_URL;
import static com.webcheckers.util.Message.info;

public class PostResignRoute implements Route
{
  final public static String RESIGN_ATTR = "resigned";
  final Map<String, Object> modeOptions = new HashMap<>(2);

  Gson gson = new Gson();

  final TemplateEngine templateEngine;


  public PostResignRoute(TemplateEngine templateEngine)
  {
    this.templateEngine = templateEngine;
  }


  @Override
  public Object handle(Request request, Response response) throws Exception
  {
    final Session session = request.session();
    final Player player = session.attribute(PLAYER_KEY);
    if (player != null)
    {
      session.attribute(RESIGN_ATTR, true);
      GameManager manager = session.attribute(GAME_MANAGER_KEY);
      int gameID = manager.getGameID(player.getUsername());
      manager.setGameOver(gameID, player.getUsername() + " has resigned" +
              ".");
      return gson.toJson(info("Resign Successful"));
    } else
    {
      response.redirect(HOME_URL);
      return null;
    }
  }

}
