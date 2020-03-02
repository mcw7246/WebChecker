package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.model.Player;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.w3c.dom.css.ViewCSS;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

import static com.webcheckers.ui.GetHomeRoute.CHALLENGE_USER_KEY;
import static spark.Spark.halt;

import com.webcheckers.util.Message;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.ui.PostRequestGameRoute;

/**
 * The {@code POST /requestResponse} route handler.
 *
 * @author Mario Castano
 */
public class PostRequestResponseRoute implements Route
{

  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  // Values used in the view-model map for rendering the receivedRequest message on screen
  static final String REQUEST_VAL = "gameRequest";
  static final String VIEW_NAME = "home.ftl";
  static final String GAME_ACCEPT = "gameAccept";

  private final PlayerLobby lobby;
  private final TemplateEngine templateEngine;

  /**
   * Constructor for the {@code GET/game} route handler.
   *
   * @param templateEngine The {@link TemplateEngine} used to render pages into HTML.
   */
  PostRequestResponseRoute(final TemplateEngine templateEngine,
                           final PlayerLobby lobby)
  {
    //validation
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    this.templateEngine = templateEngine;
    this.lobby = lobby;
  }

  /**
   * {@inheritDoc}
   * <p>
   * The handler for player response to game request that has been sent. First, a user challenges another user,
   * and a request is sent consists of the player's username and the challengee's username from
   * the list of users logged onto the playerLobby.
   */
  @Override
  public String handle(Request request, Response response)
  {
    LOG.config("Post Request Response has been invoked.");
    //retrieve the playerLobby object to verify that no time out has occurred
    final Session httpSession = request.session();
    final Player player = httpSession.attribute(GetHomeRoute.PLAYER_KEY);


    /* A null playerLobby indicates a timed out session or an illegal request on this URL.
     * In either case, we will redirect back to home.
     */
    if (player != null)
    {
      final Map<String, Object> vm = new HashMap<>();
      final String usernameStr = player.getUsername();
      vm.put(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
      final String accept = request.queryParams(GAME_ACCEPT);
      final String oppPlayer = httpSession.attribute(CHALLENGE_USER_KEY);
      LOG.config("Response to: " + oppPlayer);
      vm.put(GetHomeRoute.SIGN_IN_KEY, true);
      switch (accept)
      {
        case "yes":
          lobby.startGame(oppPlayer, usernameStr);
          response.redirect(WebServer.GAME_URL);
          break;
        case "no":
          removePlayer(usernameStr);
          response.redirect(WebServer.HOME_URL);
          break;
        //Act upon the player's response to a game request
      }
      response.redirect(WebServer.HOME_URL);
      return null;
    } else
    {
      response.redirect(WebServer.HOME_URL);
      halt();
      return null;
    }
  }

  /**
   * Removes the challenger from the victim.
   *
   * @param username the challenger's username.
   */
  static private void removePlayer(String username)
  {
    PlayerLobby.removeChallenger(username);
  }
}

