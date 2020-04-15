package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

import static com.webcheckers.ui.GetHomeRoute.GAME_MANAGER_KEY;
import static com.webcheckers.ui.GetHomeRoute.PLAYER_KEY;
import static spark.Spark.halt;
import static spark.Spark.modelAndView;

/**
 * UI controller to POST a sign out
 *
 * @author: Mikayla Wishart 'mcw7246', Sean Bergen 'sdb2139'
 **/

public class PostSignOutRoute implements Route
{


  //
  // Static methods
  //
  static final String MESSAGE_ATTR = "message";

  static final String VIEW_NAME = "home.ftl";

  private final TemplateEngine templateEngine;
  //
  // Attributes
  //
  private Player player;
  private PlayerLobby playerLobby;


  //
  // Constructor
  //
  public PostSignOutRoute(TemplateEngine templateEngine, PlayerLobby playerLobby)
  {
    // validation
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    //
    this.playerLobby = playerLobby;
    this.templateEngine = templateEngine;
  }

  @Override
  public String handle(Request request, Response response)
  {
    // retrieve the game object
    final Session session = request.session();

    this.player = session.attribute(GetHomeRoute.PLAYER_KEY);
    if (this.player != null)
    {
      if(this.player.isInGame())
      {
        GameManager manager = session.attribute(GAME_MANAGER_KEY);
        int gameID = manager.getGameID(player.getUsername());
        manager.setGameOver(gameID, player.getUsername() + " has resigned.");
      }
      //System.out.println(playerLobby.getPlayers());
      playerLobby.removePlayer(this.player);
      session.attribute(PLAYER_KEY, null);
      //System.out.println(playerLobby.getPlayers());

      //goes through the cases and submits the correct message/response

      //sign out then directs to the home page
      response.redirect(WebServer.HOME_URL);
      return null;
      //templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    } else
    {
      // handle trying to sign out while not signed in
      response.redirect(WebServer.HOME_URL);
      halt();
      return null;
    }
  }
}