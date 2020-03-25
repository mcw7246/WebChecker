package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import spark.*;

import java.util.Map;
import java.util.Set;

import static com.webcheckers.ui.PostRequestGameRoute.MESSAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

/**
 * The unit test for the {@link PostRequestResponseRoute}
 *
 * @author Mario Castano 'mac3186'
 */
@Tag("UI-tier")
public class PostRequestResponseRouteTest
{
  //Strings representing two players, one challenging the other
  private static final String PLAYER1 = "player1Username";
  private static final String PLAYER2 = "player2Username";

  //Friendly Object (assumed to work 100% as expected)
  private PlayerLobby lobby;
  private Player challengerP1;
  private Player opponentP2;

  /**
   * Mock Object Attributes for testing.
   *
   * Direct testing of these is unnecessary in this route's test,
   * but interactions between them must work as expected.
   */
  private Request request;
  private Session session;
  private Response response;
  private TemplateEngine engine;


  /**
   * The Component-Under-Test (CuT).
   * <p>
   * This route is a stateless component, so we only need one.
   * The {@link Player} component is considered a "friendly" dependency.
   */
  private PostRequestResponseRoute CuT;

  @BeforeEach
  public void setup() {
    //Mock the initialization step before the route's handler method is used and mock unfriendly interacting classes
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    engine = mock(TemplateEngine.class);
    response = mock(Response.class);

    //Initialization directly with class is safe because PlayerLobby is friendly
    lobby = new PlayerLobby();
    challengerP1 = new Player(lobby);
    opponentP2 = new Player(lobby);
    //Add the players to the lobby
    challengerP1.isValidUsername(PLAYER1);
    opponentP2.isValidUsername(PLAYER2);
    //Store information in the mock session
    when(session.attribute(GetHomeRoute.PLAYER_LOBBY_KEY)).thenReturn(lobby);
    //Setup mock calls to player classes for retrieving usernames
    //Setup a game request between the two players
    lobby.challenge(PLAYER2, PLAYER1);

    //Create a unique CuT for each test.
    CuT = new PostRequestResponseRoute(engine, lobby);
  }

  /**
   * Test that when the lobby is empty that it simply redirects to the homepage
   */
  @Test
  public void home_redirect()
  {
    when(session.attribute(GetHomeRoute.PLAYER_LOBBY_KEY)).thenReturn(null);
    try
    {
      CuT.handle(request, response);
      fail("Home found a lobby and did not halt.\n");
    }catch (spark.HaltException e){
      // Test passed.
    }
  }

  /**
   * Test that when the player responds "yes" to a challenge, that they and their opponent
   * are put into a game, and removed from the available list of challengers.
   */
  /*
  @Test
  public void opponent_accepts_challenge() {
    when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(challengerP1);
    when(session.attribute(GetHomeRoute.CHALLENGE_USER_KEY)).thenReturn(PLAYER2);
    when(request.queryParams(PostRequestResponseRoute.GAME_ACCEPT)).thenReturn("yes");

    //Test is invoked
    CuT.handle(request, response);

    //Ensure that player acceptance was handled properly
    assertFalse(lobby.getChallengers().contains(PLAYER1));
    assertFalse(lobby.getChallengers().contains(PLAYER2));
    assertTrue(challengerP1.isInGame());
    assertTrue(opponentP2.isInGame());
  }
  */

  /**
   * Test that when a player declines a challenge, both players are redirected to the homepage,
   * and are able to challenge another open player.
   */
  @Test
  public void opponent_declines_challenge() {
    when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(challengerP1);
    when(request.queryParams(PostRequestResponseRoute.GAME_ACCEPT)).thenReturn("no");

    //Test is invoked
    CuT.handle(request, response);

    //Ensure that player acceptance was handled properly
    assertTrue(lobby.getChallengers().contains(PLAYER1));
    assertFalse(lobby.getChallengers().contains(PLAYER2));
    assertFalse(challengerP1.isInGame());
    assertFalse(opponentP2.isInGame());
  }
}