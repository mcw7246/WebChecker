package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

/**
 * Class for if the user wanted to stop watching in spectator view
 * then they would be returned to the home page
 * @author Mikayla Wishart 'mcw7246'
 */
public class GetStopWatchingRoute implements Route
{
  private final TemplateEngine templateEngine;

  public GetStopWatchingRoute(TemplateEngine templateEngine){
    this.templateEngine = templateEngine;
  }

  @Override
  public Object handle(Request request, Response response){
    final Session session = request.session();

    //builds the View-Model
    ModelAndView mv;
    final Map<String, Object> vm = new HashMap<>();

    //gets the spectator player
    final Player spectator = session.attribute(GetHomeRoute.PLAYER_KEY);
    GameManager manager = session.attribute(GetHomeRoute.GAME_MANAGER_KEY);

    //redirects them to the home page
    if(spectator != null){
      //removes them from the spectator list that is stored in game manager
      manager.removeSpectator(spectator.getUsername());

      response.redirect(WebServer.HOME_URL);
    }
    else{
      response.redirect(WebServer.HOME_URL);
      halt();
    }
    return null;
  }
}
