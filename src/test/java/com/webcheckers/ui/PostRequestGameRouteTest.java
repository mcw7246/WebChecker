package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.application.ReplayManager;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

/**
 * The unit test for the {@link PostRequestGameRoute}
 *
 * @author Austin Miller 'akm8654'
 * @author Mikayla Wishart 'mcw7246'
 */
@Tag("UI-tier")
public class PostRequestGameRouteTest
{
  private static final String PLAYER1 = "Player1";
  private static final String PLAYER2 = "Player2";
  private static final String PLAYER3 = "Player3";

  /**
   * The component-under-test (CuT).
   * <p>
   * This is a stateless component so the only one needed.
   * The {@link Player} component is considered a "friendly" dependency.
   */
  private PostRequestGameRoute CuT;

  //friendly objects
  private PlayerLobby lobby;
  private GameManager manager;

  // attributes holding mock objects
  private ReplayManager rManager;
  private Request request;
  private Session session;
  private Response response;
  private TemplateEngine engine;
  private Player sender;
  private Player receiver;
  private Player other;

  @BeforeEach
  public void setup()
  {
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    engine = mock(TemplateEngine.class);
    response = mock(Response.class);
    sender = mock(Player.class);
    receiver = mock(Player.class);
    other = mock(Player.class);
    lobby = new PlayerLobby();
    rManager = new ReplayManager();
    manager = new GameManager(lobby, rManager);
    when(sender.getUsername()).thenReturn(PLAYER1);
    when(receiver.getUsername()).thenReturn(PLAYER2);
    // Have to add it to the lobby.
    lobby.newPlayer(sender);
    lobby.newPlayer(receiver);
    //Store in session
    when(session.attribute(GetHomeRoute.PLAYER_LOBBY_KEY)).thenReturn(lobby);
    when(sender.getUsername()).thenReturn(PLAYER1);
    when(receiver.getUsername()).thenReturn(PLAYER2);
    when(other.getUsername()).thenReturn(PLAYER3);

    //Create a unique CuT for each test.
    CuT = new PostRequestGameRoute();
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
    } catch (spark.HaltException e)
    {
      // Test passed.
    }
  }

  /**
   * Test that a request can be sent from sender to receiver.
   */
  @Test
  public void sender_send_request()
  {

    when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(sender);
    when(request.queryParams(PostRequestGameRoute.REQUEST_VAL)).
            thenReturn(PLAYER2);

    final TemplateEngineTest testHelper = new TemplateEngineTest();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    lobby.challenge(receiver.getUsername(), sender.getUsername());

    manager.startGame(sender.getUsername(), receiver.getUsername());
    // Invoke Test
    CuT.handle(request, response);

    //Check that the request was sent correctly
    assertTrue(lobby.getChallengers().contains(PLAYER1));
    assertEquals(lobby.getChallenges().get(PLAYER2), PLAYER1);
  }

  /**
   * Test sending a request from receiver after sender has already sent one.
   */
  @Test
  public void receiver_send_request()
  {
    //Set up lobby so that sender has sent a request to receiver. (Basically
    // run the first test above, if the above fails it's clear that this will
    // fail too.
    final TemplateEngineTest testHelper = new TemplateEngineTest();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    //First request.
    lobby.challenge(PLAYER2, PLAYER1);

    // Now Current player is receiver
    when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(receiver);
    //Challenging the sender.
    when(request.queryParams(PostRequestGameRoute.REQUEST_VAL)).
            thenReturn(PLAYER1);
    CuT.handle(request, response);

    assertFalse(lobby.getChallengers().contains(PLAYER2));
    assertFalse(lobby.challenging(PLAYER2, PLAYER1));
    assertTrue(lobby.getChallengers().contains(PLAYER1));
    assertEquals(lobby.getChallenges().get(PLAYER2), PLAYER1);
  }
  //TODO fix the errors in the tests that are commented out

  @Test
  public void two_challenge_same()
  {
    //Initialize
    final TemplateEngineTest testHelper = new TemplateEngineTest();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    //First request.
    lobby.challenge(PLAYER2, PLAYER1);
    Player newPlayer = new Player(lobby);
    newPlayer.setUsername("Player3");
    lobby.newPlayer(newPlayer);
    lobby.challenge(PLAYER1, newPlayer.getUsername());
    //Current player is Player3
    when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(other);
    //Challenging Player2 as well.
    //when(request.queryParams(PostRequestGameRoute.REQUEST_VAL)).
    //thenReturn(PLAYER2);
    CuT.handle(request, response);

    //assertFalse(lobby.getChallengers().contains(PLAYER3));
    //assertFalse(lobby.challenging(PLAYER3, PLAYER1));
    //assertTrue(lobby.getChallengers().contains(PLAYER1));
  }

  @Test
  public void another_challenge_sender()
  {
    //Initialize
    final TemplateEngineTest testHelper = new TemplateEngineTest();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
    //First request.
    lobby.challenge(PLAYER2, PLAYER1);
    //Current player is Player3
    when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(other);
    //Challenging Player1, who has already challenged Player2.
    when(request.queryParams(PostRequestGameRoute.REQUEST_VAL)).
            thenReturn(PLAYER1);
    CuT.handle(request, response);

    //assertFalse(lobby.getChallengers().contains(PLAYER3));
    //assertFalse(lobby.challenging(PLAYER3, PLAYER1));
    //assertTrue(lobby.getChallengers().contains(PLAYER1));
  }
/**
 @Test public void challenge_in_game()
 {
 //Initialize
 final TemplateEngineTest testHelper = new TemplateEngineTest();
 when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
 //Place p1 and p2 inside a game.
 lobby.challenge(PLAYER2, PLAYER1);
 gameManager.startGame(PLAYER1, PLAYER2);

 //Current player is Player3
 when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(other);
 //Challenging Player1, who is in a game with Player2.
 when(request.queryParams(PostRequestGameRoute.REQUEST_VAL)).
 thenReturn(PLAYER1);
 CuT.handle(request, response);

 assertFalse(lobby.getChallengers().contains(PLAYER3));
 assertFalse(lobby.challenging(PLAYER3, PLAYER1));
 assertTrue(gameManager.getInGame().contains(PLAYER1));
 assertTrue(gameManager.getInGame().contains(PLAYER2));
 assertFalse(gameManager.getInGame().contains(PLAYER3));

 //Do the same with challenging Player2, who is in a game with Player1
 when(request.queryParams(PostRequestGameRoute.REQUEST_VAL)).
 thenReturn(PLAYER2);
 CuT.handle(request, response);

 assertFalse(lobby.getChallengers().contains(PLAYER3));
 assertFalse(lobby.challenging(PLAYER3, PLAYER1));
 assertTrue(gameManager.getInGame().contains(PLAYER1));
 assertTrue(gameManager.getInGame().contains(PLAYER2));
 assertFalse(gameManager.getInGame().contains(PLAYER3));
 }

 @Test public void send_another_challenge()
 {
 //Initialize
 final TemplateEngineTest testHelper = new TemplateEngineTest();
 when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
 //Set up one challenge
 lobby.challenge(PLAYER2, PLAYER1);

 //Current player is Player1
 when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(other);
 //Challenging Player3
 when(request.queryParams(PostRequestGameRoute.REQUEST_VAL)).
 thenReturn(PLAYER3);

 CuT.handle(request, response);

 Map<String, String> challenges = lobby.getChallenges();
 Set<String> challengers = lobby.getChallengers();

 assertTrue(lobby.getChallengers().contains(PLAYER1));
 assertFalse(lobby.challenging(PLAYER1, PLAYER3));
 assertTrue(lobby.challenging(PLAYER1, PLAYER2));
 }*/
}
