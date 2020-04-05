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

import static com.webcheckers.ui.PostValidateMoveRoute.ACTION_DATA;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit test for the {@link PostValidateMoveRoute}
 *
 * @author Austin Miller 'akm8654'
 */
@Tag("UI-tier")
public class PostValidateMoveRouteTest
{
  private static final String MOVE_STR = "{\"start\":{\"row\":5,\"cell\":0}," +
          "\"end\":{\"row\":4,\"cell\":1}}";
  private static final String PLAYER1 = "player1";
  private static final int GAMEID = 1;

  /*
  The component under test (CuT)
   */
  private PostValidateMoveRoute CuT;

  //friendly objects

  private PlayerLobby lobby;
  private Space startSpace;
  private Piece piece;
  private Move move;
  Gson gson = new Gson();

  // attributes holding mock objects
  private GameManager manager;
  private Request request;
  private Session session;
  private Response response;
  private TemplateEngine engine;
  private Move move2;
  private Player player;
  private CheckerGame game;
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
    move = gson.fromJson(MOVE_STR, Move.class);
    move2 = mock(Move.class);
    game = mock(CheckerGame.class);
    board = mock(Board.class);
    when(request.queryParams(ACTION_DATA)).thenReturn(MOVE_STR);
    when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(player);
    lobby = new PlayerLobby();
    manager = mock(GameManager.class);
    when(session.attribute(GetHomeRoute.GAME_MANAGER_KEY)).thenReturn(manager);
    when(player.getUsername()).thenReturn(PLAYER1);
    when(manager.getGameID(PLAYER1)).thenReturn(GAMEID);
    when(manager.getLocalGame(PLAYER1)).thenReturn(game);
    startSpace = new Space(5, 0, true,
            new Piece(Piece.Color.RED));
    when(game.getBoard()).thenReturn(board);

    CuT = new PostValidateMoveRoute();
  }

  @Test
  public void home_redirect()
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


}
