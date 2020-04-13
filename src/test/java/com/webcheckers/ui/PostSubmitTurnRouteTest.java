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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

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
  private static final String INITIAL_BOARD = "src/test/java/com/webcheckers" +
          "/test-boards/InitialBoard.JSON";
  private static final String ONE_MOVE = "src/test/java/com/webcheckers/test" +
          "-boards/OneMove.JSON";
  private static final String KING_JUMP = "src/test/java/com/webcheckers/test" +
          "-boards/ToBeKingedMultiJump.JSON";
  private static final String KING_JUMP_WHITE = "src/test/java/com" +
          "/webcheckers/test" +
          "-boards/ToBeKingedMultiJumpWhite.JSON";
  private static final String REQUIRE_JUMP = "src/test/java/com/webcheckers" +
          "/test-boards/requireJumpBoard.JSON";

  /**
   * The component-under-test (CuT)
   */
  private PostSubmitTurnRoute CuT;

  //friendly attributes
  private Space jumpSpace;
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
    assertEquals("Redirected Home", CuT.handle(request, response));
  }

  @Test
  public void home_redirect_noPlayer()
  {
    when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(null);
    String returned = "";
    assertEquals("Redirected Home", CuT.handle(request, response));
  }

  @Test void local_game_no_jump()
  {
    try
    {
      this.board = gson.fromJson(new FileReader(ONE_MOVE), Board.class);
    } catch (FileNotFoundException e){
      fail("Initial Board was not found from given path");
    }
    game = new CheckerGame(player, player2, this.board);
    when(manager.getLocalGame(player.getUsername())).thenReturn(game);
    List<Move> moves = new ArrayList<>();
    moves.add(new Move(new Position(5, 0), new Position(4, 1),
            Move.MoveStatus.VALID));
    when(session.attribute(PostValidateMoveRoute.MOVE_LIST_ID)).thenReturn(moves);

    assertEquals(CuT.handle(request, response),
            gson.toJson(info("Valid Move")));
  }

  @Test void local_game_jump()
  {
    try
    {
      this.board = gson.fromJson(new FileReader(REQUIRE_JUMP), Board.class);
    } catch (FileNotFoundException e){
      fail("Initial Board was not found from given path");
    }
    CheckerGame game = new CheckerGame(player, player2, board);
    when(player.getPlayerNum()).thenReturn(1);
    when(manager.getLocalGame(player.getUsername())).thenReturn(game);

    List<Move> moves = new ArrayList<>();
    moves.add(new Move(new Position(4, 1), new Position(2, 3),
            Move.MoveStatus.JUMP));

    Space endSpace = new Space(2, 3, true, new Piece(Piece.Color.RED));
    when(session.attribute(PostValidateMoveRoute.MOVE_LIST_ID)).thenReturn(moves);
    assertEquals(gson.toJson(info("Valid Move")), CuT.handle(request,
            response));
  }

  @Test public void test_king_multi_jump(){
    when(manager.getLocalGame(player.getUsername())).thenReturn(game);
    Space jumpSpace2 = new Space(3, 4, true,
            new Piece(Piece.Color.WHITE));
    Space endSpace = new Space(4, 5, true,
            new Piece(Piece.Color.RED, Piece.Type.KING));
    jumpSpace = new Space(1, 2, true, new Piece(Piece.Color.WHITE));

    List<Move> moves = new ArrayList<>();
    moves.add(new Move(new Position(0, 1), new Position(2, 3), Move.MoveStatus.JUMP));
    moves.add(new Move(new Position(2, 3), new Position(4, 5), Move.MoveStatus.JUMP));

    when(board.getSpaceAt(4, 5)).thenReturn(endSpace);
    when(session.attribute(PostValidateMoveRoute.MOVE_LIST_ID)).thenReturn(moves);
    game.addJumpedPieces(jumpSpace2);
    game.addJumpedPieces(jumpSpace);

    assertEquals(gson.toJson(info("Valid Move")), CuT.handle(request,
            response));
    assertNull(jumpSpace2.getPiece());
  }

  @Test
  public void red_king()
  {
    try
    {
      this.board = gson.fromJson(new FileReader(KING_JUMP), Board.class);
    } catch (FileNotFoundException e){
      fail("Initial Board was not found from given path");
    }
    game = new CheckerGame(player, player2, this.board);
    when(manager.getLocalGame(player.getUsername())).thenReturn(game);
    List<Move> moves = new ArrayList<>();
    moves.add(new Move(new Position(2, 5), new Position(0, 7),
            Move.MoveStatus.JUMP));
    when(session.attribute(PostValidateMoveRoute.MOVE_LIST_ID)).thenReturn(moves);
    assertEquals(gson.toJson(info("Valid Move")), CuT.handle(request,
            response));
    assertEquals(Piece.Type.KING, board.getSpaceAt(0, 7).getPiece().getType());
  }

  @Test
  public void white_king()
  {
    try
    {
      this.board = gson.fromJson(new FileReader(KING_JUMP_WHITE), Board.class);
    } catch (FileNotFoundException e){
      fail("Initial Board was not found from given path");
    }
    game = new CheckerGame(player, player2, this.board);
    when(manager.getLocalGame(player.getUsername())).thenReturn(game);
    List<Move> moves = new ArrayList<>();
    moves.add(new Move(new Position(2, 5), new Position(0, 7),
            Move.MoveStatus.JUMP));
    when(session.attribute(PostValidateMoveRoute.MOVE_LIST_ID)).thenReturn(moves);
    assertEquals(gson.toJson(info("Valid Move")), CuT.handle(request,
            response));
    assertEquals(Piece.Type.KING, board.getSpaceAt(0, 7).getPiece().getType());
  }

  @Test
  public void opponent_cannot_move()
  {
    //TODO
  }
}


