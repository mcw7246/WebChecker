package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.model.Player;
import org.w3c.dom.css.ViewCSS;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;
import static spark.Spark.halt;

import com.webcheckers.util.Message;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.ui.PostSignInRoute;

/**
 * The {@code POST /requestGame} route handler.
 *
 * @author Mario Castano
 */
public class PostRequestGameRoute implements Route {

  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  // Values used in the view-model map for rendering the Challenge Player/requestGame screen
  static final String CHALLENGER_ATTR = "username";
  static final String MESSAGE = "message";
  static final String VIEW_NAME = "home.ftl";
  static final String REQUEST_VAL = "gameRequest";

  private final TemplateEngine templateEngine;

  /**
   * Constructor for the {@code GET/game} route handler.
   *
   * @param templateEngine
   *      The {@link TemplateEngine} used to render pages into HTML.
   */
  PostRequestGameRoute(final TemplateEngine templateEngine) {
    //validate
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    this.templateEngine = templateEngine;
  }

  /**
   * {@inheritDoc}
   *
   * The handler for requests by players to start a game. First, a user challenges another user,
   * and a request is sent consists of the player's username and the challengee's username from
   * the list of users logged onto the playerLobby.
   */
  @Override
  public String handle(Request request, Response response) {
    //retrieve the playerLobby object from which the usernames of logged in players can be retrieved
    final Session httpSession = request.session();
    final PlayerLobby playerLobby =
            httpSession.attribute(GetHomeRoute.PLAYER_LOBBY_KEY);
    final Player player = httpSession.attribute(GetHomeRoute.PLAYER_KEY);

    /* A null playerLobby indicates a timed out session or an illegal request on this URL.
     * In either case, we will redirect back to home.
     */
    if(playerLobby != null)
    {
      final Map<String, Object> vm = new HashMap<>();
      final String usernameStr = request.queryParams(REQUEST_VAL);
      if (playerLobby.challenge(usernameStr, player.getUsername()))
      {
        vm.put(MESSAGE, "Request Not Sent! " + usernameStr +" has already " +
                "been challenged!");
      } else
      {
        if (playerLobby.challenging(player.getUsername())){
          vm.put(MESSAGE, "Request Not Sent! " + usernameStr + " is already" +
                  "challenging someone!");
        } else
        {
          vm.put(MESSAGE, "Request sent to " + usernameStr + ".");
        }
      }
      return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
    else
    {
      response.redirect(WebServer.HOME_URL);
      halt();
      return null;
    }
  }

}
