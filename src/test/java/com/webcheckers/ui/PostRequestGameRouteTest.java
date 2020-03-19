package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@Tag("UI-tier")
public class PostRequestGameRouteTest
{

  private static final String PLAYER1 = "Player1";
  private static final String PLAYER2 = "Player2";

  /**
   * The component-under-rest (CuT).
   * <p>
   * This is a stateless component so the only one needed.
   * The {@link Player} component is considered a "friendly" dependency.
   */
  private PostRequestGameRoute CuT;

  //friendly objects
  private PlayerLobby lobby;

  // attributes holding mock objects
  private Request request;
  private Session session;
  private Response response;
  private TemplateEngine engine;
  private Player sender;
  private Player receiver;


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
    lobby = new PlayerLobby();
    when(sender.getUsername()).thenReturn(PLAYER1);
    when(receiver.getUsername()).thenReturn(PLAYER2);
    // Have to add it to the lobby.
    lobby.newPlayer(sender);
    lobby.newPlayer(receiver);
    //Store in session
    when(session.attribute(GetHomeRoute.PLAYER_LOBBY_KEY)).thenReturn(lobby);

    //TODO: Find which parts use the session

    //Create a unique CuT for each test.
    CuT = new PostRequestGameRoute(engine);
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

    // Invoke Test
    CuT.handle(request, response);

    // Analyze results.
    testHelper.assertViewModelExists();

  }

  /**
   * Test sending a request from receiver after sender has already sent one.
   */
  @Test
  public void receiver_send_request()
  {
    //Current player is receiver
    when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(receiver);
    //Challenging the sender.
    when(request.queryParams(PostRequestGameRoute.REQUEST_VAL)).
            thenReturn(PLAYER1);
    //Set up lobby so that sender has sent a request to receiver.

  }

}
