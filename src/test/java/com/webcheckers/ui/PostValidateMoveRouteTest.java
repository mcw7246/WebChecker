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
import static com.webcheckers.util.Message.error;
import static com.webcheckers.util.Message.info;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
  private static String MOVE_STR = "{\"start\":{\"row\":5,\"cell\":0}," +
          "\"end\":{\"row\":4,\"cell\":1}}";
  private static final String PLAYER1 = "player1";
  private static final int GAMEID = 1;
  private static final String INVALID_SPACE = "Invalid Move: Space is the " +
          "wrong color!";
  private static final String ALREADY_MOVED = "Already Moved: Already moved a" +
          " piece!";
  private static final String VALID  = "Valid Move! Click submit to send";
  private static final String JUMP = "Jump Move! Click submit to send.";
  private static final String OCCUPIED = "Invalid Move: Space is already " +
          "Occupied!";
  private static final String TOO_FAR = "Invalid Move: Space is too far away!";
  private static final String SAME_SPACE = "Invalid Move: The Space you've " +
          "tried to move to is the same one!";
  private static final String INVALID_BACKWARDS = "Invalid Move: You aren't " +
          "kinged yet! You must move forward!";
  private static final String JUMP_OWN = "Invalid Move: You're attempting to " +
          "jump your own piece!";
  private static final String INVALID_DIR = "Invalid Move: You must move " +
          "diagonally!";
  private static final int START_ROW = 5;
  private static final int START_COL = 0;

  /*
  The component under test (CuT)
   */
  private PostValidateMoveRoute CuT;

  //friendly objects
  private int endRow;
  private int endCol;
  private PlayerLobby lobby;
  private Space startSpace;
  private Space endSpace;
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
    startSpace = new Space(START_ROW, START_COL, true,
            new Piece(Piece.Color.RED));
    when(game.getBoard()).thenReturn(board);
    when(board.getSpaceAt(START_ROW, START_COL)).thenReturn(startSpace);
    endSpace = new Space(4, 1, true);
    CuT = new PostValidateMoveRoute();
  }

  /**
   * Updates the MoveStr to a new JSON as needed.
   *
   * @param endRow the row to be changed
   * @param endCol the col to be changed.
   * @return the updated String.
   */
  public String changeMoveStr(int endRow, int endCol)
  {
    return "{\"start\":{\"row\":5,\"cell\":0}," +
            "\"end\":{\"row\":" + endRow + ",\"cell\":" + endCol + " }}";
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

  @Test
  public void home_redirect_local_game()
  {
    when(manager.getLocalGame(player.getUsername())).thenReturn(null);
    when(manager.makeClientSideGame(GAMEID, player.getUsername())).thenReturn(null);
    try
    {
      CuT.handle(request, response);
      fail("Home found a lobby and did not halt.\n");
    } catch (spark.HaltException e)
    {
      // Test passed.
    }
  }

  @Test
  public void make_Game_and_Valid()
  {

    when(manager.getLocalGame(player.getUsername())).thenReturn(null);
    when(manager.makeClientSideGame(GAMEID, player.getUsername())).thenReturn(game);
    endRow = 4;
    endCol = 1;
    endSpace = new Space(endRow, endCol, true);
    when(board.getSpaceAt(endRow, endCol)).thenReturn(endSpace);
    assertEquals(gson.toJson(info(VALID)), CuT.handle(request,
            response));
  }

  @Test
  public void already_moved()
  {
    when(game.hasMoved()).thenReturn(true);
    endRow = 4;
    endCol = 1;
    endSpace = new Space(endRow, endCol, true);
    when(board.getSpaceAt(endRow, endCol)).thenReturn(endSpace);
    assertEquals(gson.toJson(error(ALREADY_MOVED)), CuT.handle(request,
            response));
  }

}
