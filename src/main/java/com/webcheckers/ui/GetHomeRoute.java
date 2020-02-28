package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 * @author Austin Miller 'akm8654@rit.edu'
 */
public class GetHomeRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

  static final String TITLE_ATTR = "title";
  static final String NEW_PLAYER_ATTR = "newPlayer";
  static final String SIGN_IN_ATTR = "signIn";

  static final String TITLE = "Welcome to WebCheckers! Please signin.";
  static final String PLAYER_SERVICES_KEY = "playerServices";
  static final String PLAYER_LOBBY_KEY ="playerLobby";


  static final String USERNAMES= "usernames";

  private final TemplateEngine templateEngine;
  private final PlayerLobby playerLobby;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetHomeRoute(final PlayerLobby playerLobby,
                      TemplateEngine templateEngine) {
    // validation
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby" +
            "is required.");
    //
    LOG.config("GetHomeRoute is initialized.");
  }

  /**
   * Render the WebCheckers Home page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("GetHomeRoute is invoked.");
    final Session httpSession = request.session();
    //
    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Welcome!");

    // display a user message in the Home page
    vm.put("message", WELCOME_MSG);

    if(httpSession.attribute(PLAYER_SERVICES_KEY) == null) {
      //Tell the user to log-in
    } else {
      // display the player lobby if the player is signed in and has more than
      // one player.
      if (playerLobby.hasOpponents()) {
        vm.put(USERNAMES, this.playerLobby.getUsernames());
      }
    }
    // render the View
    return templateEngine.render(new ModelAndView(vm , "home.ftl"));
  }
}
