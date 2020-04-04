package com.webcheckers.ui;


import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static com.webcheckers.util.Message.info;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit test for the {@link PostSubmitTurnRoute}
 *
 * @author Austin Miler 'akm8654'
 */
@Tag("UI-tier")
public class PostSubmitTurnRouteTest
{
  private static final int GAME_ID = 1;
  private static final String PLAYER1 = "player1";

  /**
   * The component-under-test (CuT)
   */
  private PostSubmitTurnRoute CuT;

  //friendly attributes
  private Space jumpSpace;
  private Piece piece;
  private CheckerGame game;
  Gson gson = new Gson();

  //attributes holding mock objects
  private GameManager manager;
  private PlayerLobby lobby;
  private Request request;
  private Session session;
  private Response response;
  private TemplateEngine engine;
  private Player player;
  private Player player2;
  private Board board;

  @BeforeEach
  public void setup()
  {
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    engine = mock(TemplateEngine.class);
    response = mock(Response.class);
    player = mock(Player.class);
    player2 = mock(Player.class);
    board = mock(Board.class);
    when(player.getUsername()).thenReturn(PLAYER1);
    lobby = mock(PlayerLobby.class);
    manager = mock(GameManager.class);
    when(session.attribute(GetHomeRoute.GAME_MANAGER_KEY)).thenReturn(manager);
    when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(player);
    when(manager.getGameID(player.getUsername())).thenReturn(GAME_ID);
    game = new CheckerGame(player, player2, board);
    CuT = new PostSubmitTurnRoute();
    //when(manager.getLocalGame(player.getUsername())).thenReturn(game);
  }

  @Test
  public void home_redirect_noGame()
  {
    when(manager.getLocalGame(player.getUsername())).thenReturn(null);
    when(manager.getGame(GAME_ID)).thenReturn(null);

    try
    {
      CuT.handle(request, response);
      fail("Home found a game somehow and did not halt.");
    } catch (spark.HaltException e)
    {
      //Test passed.
    }
  }

  @Test
  public void home_redirect_noPlayer()
  {
    when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(null);
    try
    {
      CuT.handle(request, response);
      fail("Home found a game somehow and did not halt.");
    } catch (spark.HaltException e)
    {
      //Test passed.
    }
  }

  @Test void local_game_no_jump()
  {
    when(manager.getLocalGame(player.getUsername())).thenReturn(game);

    assertEquals(CuT.handle(request, response),
            gson.toJson(info("valid move")));
    //when(game.getJumpedPiece()).thenReturn(new Space(0, 0, true,
      //      new Piece(Piece.Color.WHITE)));
  }

  @Test void local_game_jump()
  {
    when(manager.getLocalGame(player.getUsername())).thenReturn(game);
    Space jumpSpace = new Space(0, 0,
            true, new Piece(Piece.Color.WHITE));
    game.addJumpedPieces(jumpSpace);
    assertEquals(gson.toJson(info("valid move")), CuT.handle(request,
            response));
    assertNull(jumpSpace.getPiece());
  }
}
