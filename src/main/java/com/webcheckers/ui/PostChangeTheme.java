package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import static com.webcheckers.ui.GetHomeRoute.GAME_MANAGER_KEY;
import static com.webcheckers.ui.GetHomeRoute.PLAYER_KEY;
import static com.webcheckers.ui.WebServer.*;

public class PostChangeTheme implements Route
{
  public static String THEME = "theme";

  @Override
  public Object handle(Request request, Response response) throws Exception
  {
    Session session = request.session();
    String theme = session.attribute(THEME);
    GameManager manager = session.attribute(GAME_MANAGER_KEY);
    Player player = session.attribute(PLAYER_KEY);
    if (player != null)
    {
      if (theme == null)
      {
        session.attribute(THEME, "pink");
      } else
      {
        session.attribute(THEME, null);
      }
      if (player.isInGame())
      {
        response.redirect(GAME_URL);
      } else if (manager.isSpectator(player.getUsername()))
      {
        response.redirect(SPECTATOR_GAME_URL);
      } else
      {
        response.redirect(HOME_URL);
      }
    } else
    {
      response.redirect(HOME_URL);
    }
    return null;
  }
}
