package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import org.eclipse.jetty.io.NegotiatingClientConnection;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

import static com.webcheckers.ui.GetHomeRoute.CHALLENGE_USER_KEY;
import static spark.Spark.halt;

/**
 * The {@code POST /requestResponse} route handler.
 *
 * @author Mario Castano
 */
public class PostRequestResponseRoute implements Route
{

  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
  static final String GAME_ACCEPT = "gameAccept";
  private final PlayerLobby lobby;
  private static final String KING_JUMP = "src/test/java/com/webcheckers/test" +
          "-boards/ToBeKingedMultiJumpWhite.JSON";
  private static final String REQUIRE_JUMP = "src/test/java/com/webcheckers" +
          "/test-boards/requireJumpBoard.JSON";
  private static final String NECESSARY_JUMP_WHITE = "src/test/java/com" +
          "/webcheckers/test-boards/necesssaryJumpWhite.JSON";
  private static final String MULTI_JUMP_STILL_REQUIRED_WRONG = "src/test" +
          "/java/com/webcheckers/test-boards/multiJumpBoardStillJump.JSON";
  private static final String NO_MORE_MOVES = "src/test/java/com/webcheckers" +
          "/test-boards/no-more-moves.JSON";
  private static final String ABOUT_JUMP_ALL = "src/test/java/com/webcheckers" +
          "/test-boards/about-to-all-pieces-jumped.JSON";
  private static final String DEMO_1 = "src/test/java/com/webcheckers/test" +
          "-boards/test-demo-1.JSON";

  /**
   * Constructor for the {@code GET/game} route handler.
   *
   * @param lobby: the player lobby for the Response.
   */
  PostRequestResponseRoute(final PlayerLobby lobby)
  {
    //validation
    this.lobby = lobby;
  }

  private void startGameOrTest(String username, String opponent,
                               GameManager manager)
  {
    if (username.equals("TEST MULTI KING JUMP"))
    {
      manager.startTestGame(username, opponent, KING_JUMP, 2);
    } else if (username.equals("TEST REQUIRE JUMP"))
    {
      manager.startTestGame(username, opponent, REQUIRE_JUMP, 1);
    } else if (username.equals("TEST NECESSARY WHITE"))
    {
      manager.startTestGame(username, opponent, NECESSARY_JUMP_WHITE, 2);
    } else if (username.equals("TEST NO MORE MOVES"))
    {
      manager.startTestGame(username, opponent, NO_MORE_MOVES, 1);
    } else if (username.equals("TEST MULTI JUMP REQUIRE"))
    {
      manager.startTestGame(username, opponent,
              MULTI_JUMP_STILL_REQUIRED_WRONG, 1);
    } else if (username.equals("ABOUT TO JUMP ALL"))
    {
      manager.startTestGame(username, opponent, ABOUT_JUMP_ALL, 1);
    } else if (username.equals("TEST DEMO 1")){
      manager.startTestGame(username, opponent, DEMO_1, 1);
  } else

    {
      manager.startGame(username, opponent);
    }
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
      final String usernameStr = player.getUsername();
      final String accept = request.queryParams(GAME_ACCEPT);
      String oppPlayer = httpSession.attribute(CHALLENGE_USER_KEY);
      oppPlayer = oppPlayer.replace('-', ' ');
      GameManager gameManager = httpSession.attribute(GetHomeRoute.GAME_MANAGER_KEY);
      LOG.config("Response to: " + oppPlayer);
      switch (accept)
      {
        case "yes":
          lobby.removeChallenger(usernameStr);
          startGameOrTest(oppPlayer, usernameStr, gameManager);
          response.redirect(WebServer.GAME_URL);
          break;
        case "no":
          removePlayer(usernameStr);
          response.redirect(WebServer.HOME_URL);
          break;
        //Act upon the player's response to a game request
      }
    } else
    {
      response.redirect(WebServer.HOME_URL);
      halt();
    }
    return null;
  }

  /**
   * Removes the challenger from the victim.
   *
   * @param username the challenger's username.
   */
  private void removePlayer(String username)
  {
    lobby.removeChallenger(username);
  }
}

