package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * Class for if the user wanted to stop watching in spectator view
 * then they would be returned to the home page
 */
public class GetStopWatchingRoute implements Route
{
  private final TemplateEngine templateEngine;
  //private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

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

    if(spectator != null){
      manager.removeSpectator(spectator.getUsername());
      System.out.println(manager.getSpectators());

      response.redirect(WebServer.HOME_URL);

    }
    else{
      response.redirect(WebServer.HOME_URL);
      halt();
    }
    return null;
  }
  /**



/**
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
      // start building the View-Model
      ModelAndView mv;
      final Map<String, Object> vm = new HashMap<>();
      vm.put(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);

      // retrieve the game object
      final Session session = request.session();

      this.player = session.attribute(GetHomeRoute.PLAYER_KEY);
      if (this.player != null)
      {
        //System.out.println(playerLobby.getPlayers());
        playerLobby.removePlayer(this.player);
        //System.out.println(playerLobby.getPlayers());

        //goes through the cases and submits the correct message/response

        //sign out then directs to the home page
        response.redirect(WebServer.HOME_URL);
        //templateEngine.render(new ModelAndView(vm, VIEW_NAME));
      } else
      {
        // handle trying to sign out while not signed in
        response.redirect(WebServer.HOME_URL);
        halt();
      }
      return null;
    }

    /**
     * Helper method that notifies a user of unexpected responses or other internal errors that occur
     *
     * @param vm      the View-Model map to update in case of error
     * @param message the message associated with the error that occurred
     * @return the updated View to notify the user that an error had occurred
     *//**
    private ModelAndView error(final Map<String, Object> vm, final String message)
    {
      vm.put(MESSAGE_ATTR, Message.error(message));
      return new ModelAndView(vm, VIEW_NAME);
    }

    private void available(Response response)
    {
      response.redirect(WebServer.HOME_URL);
      halt();
    }
  }*/
}
