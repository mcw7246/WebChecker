package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static com.webcheckers.util.Message.info;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit test for the {@link PostValidateMoveRoute}
 *
 * @author Mario Castano 'mac3186'
 */
public class PostCheckTurnRouteTest
{
  private static final String PLAYER1 = "player1";
  private static final String PLAYER2 = "player2";
  private static final int GAMEID = 1;

  /*
  The component under test (CuT)
  */
  private PostCheckTurnRoute CuT;

  // Friendly attributes
  Gson gson = new Gson();

  // Mock attributes
  private GameManager manager;
  private Request request;
  private Session session;
  private Response response;
  private Player player;
  private CheckerGame game;

  /**
   * Setup the mock ui and model tier components to test route.
   */
  @BeforeEach
  public void setup()
  {
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    response = mock(Response.class);
    player = mock(Player.class);
    game = mock(CheckerGame.class);
    when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(player);
    manager = mock(GameManager.class);
    when(session.attribute(GetHomeRoute.GAME_MANAGER_KEY)).thenReturn(manager);
    when(player.getUsername()).thenReturn(PLAYER1);
    when(manager.getGameID(PLAYER1)).thenReturn(GAMEID);
    when(manager.getGame(GAMEID)).thenReturn(game);
    CuT = new PostCheckTurnRoute();
  }

  /**
   * Ensure that if the player object isn't found, that the route halts and redirects User to home.
   */
  @Test
  public void redirect_to_home()
  {
    when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(null);
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
   * Ensure that if it is the User player's turn, that the Json message indicates "true"
   * for this player's turn.
   */
  @Test
  public void player_turn()
  {
    when(game.getTurn()).thenReturn(PLAYER1);
    assertEquals(gson.toJson(info("true")), CuT.handle(request, response));
  }

  /**
   * Ensure that if it is the opponent of the User's turn, that the Json message indicates
   * "false" for this player's turn.
   */
  @Test
  public void not_player_turn()
  {
    when(game.getTurn()).thenReturn(PLAYER2);
    assertEquals(gson.toJson(info("false")), CuT.handle(request, response));
  }


}
