package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static com.webcheckers.ui.GetHomeRoute.ERROR_MESSAGE_KEY;
import static com.webcheckers.util.Message.error;
import static com.webcheckers.util.Message.info;
import static spark.Spark.halt;

/**
 * The {@code POST /requestGame} route handler.
 *
 * @author Austin Miller 'akm8654'
 * @author Mario Castano
 */
public class PostRequestGameRoute implements Route
{

  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  // Values used in the view-model map for rendering the Challenge Player/requestGame screen
  static final String CHALLENGER_ATTR = "challengeUser";
  static final String MESSAGE = "message";
  static final String VIEW_NAME = "home.ftl";
  static final String REQUEST_VAL = "gameRequest";

  private final TemplateEngine templateEngine;

  /**
   * Constructor for the {@code GET/game} route handler.
   *
   * @param templateEngine The {@link TemplateEngine} used to render pages into HTML.
   */
  PostRequestGameRoute(final TemplateEngine templateEngine)
  {
    //validate
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    this.templateEngine = templateEngine;
  }

  /**
   * {@inheritDoc}
   * <p>
   * The handler for requests by players to start a game. First, a user challenges another user,
   * and a request is sent consists of the player's username and the challengee's username from
   * the list of users logged onto the playerLobby.
   */
  @Override
  public String handle(Request request, Response response)
  {
    LOG.config("PostRequestGame has been invoked");
    //retrieve the playerLobby object from which the usernames of logged in players can be retrieved
    final Session httpSession = request.session();
    final GameManager gameManager = httpSession.attribute(GetHomeRoute.GAME_MANAGER_KEY);
    final PlayerLobby playerLobby =
            httpSession.attribute(GetHomeRoute.PLAYER_LOBBY_KEY);
    final Player player = httpSession.attribute(GetHomeRoute.PLAYER_KEY);

    /* A null playerLobby indicates a timed out session or an illegal request on this URL.
     * In either case, we will redirect back to home.
     */
    if (playerLobby != null)
    {
      final Map<String, Object> vm = new HashMap<>();
      vm.put(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
      // person who is being challenged.
      final String challengerStr = request.queryParams(REQUEST_VAL);
      String username = player.getUsername();
      if (playerLobby.getChallenges().get(username) != null)
      {
        httpSession.attribute(ERROR_MESSAGE_KEY, "Request not Sent! You have a pending request.");
        response.redirect(WebServer.HOME_URL);
        return null;
      } else if (playerLobby.getChallengers().contains(username)){
        httpSession.attribute(ERROR_MESSAGE_KEY, "Request Not Sent! You've already" +
                " sent a challenge!");
        response.redirect(WebServer.HOME_URL);
        return null;
      } else if (gameManager.getInGame().contains(challengerStr))
      {
        httpSession.attribute(ERROR_MESSAGE_KEY, "Request not Sent! " +
                challengerStr + " is already in a game.");
      } else if (!playerLobby.challenge(challengerStr, username))
      {
        httpSession.attribute(ERROR_MESSAGE_KEY, "Request Not Sent! " +
                challengerStr + " has already been challenged.");
      } else
      {
        if (playerLobby.challenging(challengerStr, username))
        {
          httpSession.attribute(ERROR_MESSAGE_KEY, "Request Not Sent! " +
                  challengerStr + " is already challenging someone!");
        } else
        {
          httpSession.attribute(MESSAGE, "Request sent to " + challengerStr +
                  ".");
        }
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

}
