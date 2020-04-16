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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.webcheckers.util.Message.error;
import static com.webcheckers.util.Message.info;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit test for the {@link PostSubmitTurnRoute}
 *
 * @author Austin Miler 'akm8654'
 * @author Mikayla Wishart 'mcw7246'
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
  private RequireMove requireMove;

  @BeforeEach
  public void setup()
  {
    game = new CheckerGame(player, player2, board);

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
    requireMove = mock(RequireMove.class);
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
      game = new CheckerGame(player, player2, board);
    } catch (FileNotFoundException e){
      fail("Initial Board was not found from given path");
    }
    int gameID = manager.getGameID(player.getUsername());
    when(manager.getLocalGame(player.getUsername())).thenReturn(game);
    when(manager.getGameID(player.getUsername())).thenReturn(gameID);
    List<Move> moves = new ArrayList<>();
    moves.add(new Move(new Position(5, 0), new Position(4, 1),
            Move.MoveStatus.VALID));
    when(manager.getGame(gameID)).thenReturn(game);
    when(session.attribute(PostValidateMoveRoute.MOVE_LIST_ID)).thenReturn(moves);
    when(manager.getLocalGame(player.getUsername())).thenReturn(game);

    assertEquals(CuT.handle(request, response),
            gson.toJson(info("Valid Move")));
  }

  /**
   * tests a jump being made
   */
  @Test void local_game_jump()
  {
    try
    {
      this.board = gson.fromJson(new FileReader(REQUIRE_JUMP), Board.class);
    } catch (FileNotFoundException e){
      fail("Initial Board was not found from given path");
    }
    CheckerGame game = new CheckerGame(player, player2, board);
    int gameID = manager.getGameID(player.getUsername());
    when(player.getPlayerNum()).thenReturn(1);
    when(manager.getLocalGame(player.getUsername())).thenReturn(game);
    when(manager.getGameID(player.getUsername())).thenReturn(gameID);
    when(manager.getGame(gameID)).thenReturn(game);
    List<Move> moves = new ArrayList<>();
    moves.add(new Move(new Position(4, 1), new Position(2, 3),
            Move.MoveStatus.JUMP));

    Space endSpace = new Space(2, 3, true, new Piece(Piece.Color.RED));
    when(session.attribute(PostValidateMoveRoute.MOVE_LIST_ID)).thenReturn(moves);
    game.makeMove(moves.get(0));
    when(manager.getLocalGame(player.getUsername())).thenReturn(game);
    assertEquals(gson.toJson(info("Valid Move")), CuT.handle(request,
            response));
  }

  /**
   * tests a king making a multijump
   */
  @Test public void test_king_multi_jump(){
    when(manager.getLocalGame(player.getUsername())).thenReturn(game);

    //creates all the pieces used in the test
    Piece redPiece = new Piece(Piece.Color.RED, Piece.Type.KING);
    Piece whitePieceOne = new Piece(Piece.Color.WHITE, Piece.Type.SINGLE);
    Piece whitePieceTwo = new Piece(Piece.Color.WHITE, Piece.Type.SINGLE);

    //creates the gameID
    int gameID = manager.getGameID(player.getUsername());

    //creates the spaces used in the test
    Space startSpace = new Space(0, 1, true, redPiece);
    Space jumpSpaceOne = new Space(1, 2, true, whitePieceOne);
    Space jumpSpaceTwo = new Space(3, 4, true, whitePieceTwo);
    Space endSpaceOne = new Space(2, 3, true);
    Space endSpaceTwo = new Space(4, 5, true);

    //when the spaces are called then it will return the ones created in the test
    when(board.getSpaceAt(startSpace.getRowIndex(), startSpace.getColumnIndex())).thenReturn(startSpace);
    when(board.getSpaceAt(endSpaceOne.getRowIndex(), endSpaceOne.getColumnIndex())).thenReturn(endSpaceOne);

    when(board.getSpaceAt(endSpaceTwo.getRowIndex(), endSpaceTwo.getColumnIndex())).thenReturn(endSpaceTwo);

    when(manager.getGameID(player.getUsername())).thenReturn(gameID);

    List<Move> moves = new ArrayList<>();

    moves.add(new Move(new Position(startSpace.getRowIndex(), startSpace.getColumnIndex()), new Position(endSpaceOne.getRowIndex(), endSpaceOne.getColumnIndex()), Move.MoveStatus.JUMP));

    moves.add(new Move(new Position(endSpaceOne.getRowIndex(), endSpaceOne.getColumnIndex()), new Position(endSpaceTwo.getRowIndex(), endSpaceTwo.getColumnIndex()), Move.MoveStatus.JUMP));

    game.makeMove(moves.get(0));
    game.makeMove(moves.get(1));

    when(session.attribute(PostValidateMoveRoute.MOVE_LIST_ID)).thenReturn(moves);

    game.addJumpedPieces(jumpSpaceOne);
    game.addJumpedPieces(jumpSpaceTwo);

    //CuT.handle(request, response);
    assertEquals(gson.toJson(info("Valid Move")), CuT.handle(request,
            response));
    assertNull(jumpSpaceTwo.getPiece());
  }

  /**
   * tests a red piece being made a king when needed
   */
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
    when(session.attribute(GetHomeRoute.GAME_MANAGER_KEY)).thenReturn(manager);
    List<Move> moves = new ArrayList<>();

    int gameID = manager.getGameID(player.getUsername());

    when(manager.getGame(gameID)).thenReturn(game);

    Space startSpace = new Space(5, 5, true);
    Space jumpSpace = new Space(6, 6, true);
    Space endSpace = new Space(7, 7, true);

    Piece jumpPiece = new Piece(Piece.Color.RED, Piece.Type.SINGLE);
    Piece jumpedPiece = new Piece(Piece.Color.WHITE, Piece.Type.SINGLE);

    startSpace.setPiece(jumpPiece);
    jumpSpace.setPiece(jumpedPiece);

    Move moveMade = new Move(new Position(startSpace.getRowIndex(), startSpace.getColumnIndex()), new Position(endSpace.getRowIndex(),endSpace.getColumnIndex()), Move.MoveStatus.JUMP);
    moves.add(moveMade);

    when(session.attribute(PostValidateMoveRoute.MOVE_LIST_ID)).thenReturn(moves);
    //CuT.handle(request, response);
    assertEquals(gson.toJson(info("Valid Move")), CuT.handle(request,
            response));
    //assertEquals(Piece.Type.KING, board.getSpaceAt(0, 7).getPiece().getType());
  }

  /**
   * Tests a white piece being made a king when necessary
   */
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
    when(session.attribute(GetHomeRoute.GAME_MANAGER_KEY)).thenReturn(manager);

    //makes it the white piece's turn
    game.updateTurn();

    int gameID = manager.getGameID(player.getUsername());

    when(manager.getGame(gameID)).thenReturn(game);

    Piece redPiece = new Piece(Piece.Color.RED, Piece.Type.SINGLE);
    Piece whitePiece = new Piece(Piece.Color.WHITE, Piece.Type.SINGLE);

    Space start = new Space(5, 5, true, whitePiece);
    Space jumped = new Space(6, 6, true, redPiece);
    Space end = new Space(7, 7, true);

    Move moveMade = new Move(new Position(start.getRowIndex(), start.getColumnIndex()), new Position(end.getRowIndex(), end.getColumnIndex()), Move.MoveStatus.JUMP);

    game.makeMove(moveMade);

    List<Move> moveList = new ArrayList<>();
    moveList.add(moveMade);


    game.addJumpedPieces(jumped);
    game.getBoard().getSpaceAt(end.getRowIndex(), end.getColumnIndex()).setPiece(whitePiece);
    when(session.attribute(PostValidateMoveRoute.MOVE_LIST_ID)).thenReturn(moveList);

    assertEquals(gson.toJson(info("Valid Move")), CuT.handle(request, response));
    assertEquals(Piece.Type.KING, game.getBoard().getSpaceAt(end.getRowIndex(), end.getColumnIndex()).getPiece().getType());
  }

  @Test
  public void opponent_cannot_move()
  {
    //TODO
  }

  @Test
  public void required_move_not_made(){
    //TODO: FINISH THIS TEST

    try{
      this.board = gson.fromJson(new FileReader(REQUIRE_JUMP), Board.class);
    }catch(FileNotFoundException ex){
      fail("Initial board was not found from given path.");
    }
    when(manager.getGameID(player.getUsername())).thenReturn(GAME_ID);
    when(manager.getGame(GAME_ID)).thenReturn(game);


















    /**
    Piece piece = new Piece(Piece.Color.RED, Piece.Type.SINGLE);
    Space start = new Space(0,0,true, piece);
    Space end = new Space(1, 2, true);

    int gameID = manager.getGameID(player.getUsername());

    when(manager.getGame(gameID)).thenReturn(game);

    when(manager.getLocalGame(player.getUsername())).thenReturn(game);

    when(session.attribute(GetHomeRoute.GAME_MANAGER_KEY)).thenReturn(manager);

    List<Move> movesMade = new ArrayList<>();
    //normal move made
    Move move = new Move(new Position(start.getRowIndex(), start.getColumnIndex()), new Position(end.getRowIndex(), end.getColumnIndex()));

    movesMade.add(move);
    when(session.attribute(PostValidateMoveRoute.MOVE_LIST_ID)).thenReturn(movesMade);
    board.getSpaceAt(end.getRowIndex(), end.getColumnIndex()).setPiece(piece);

    System.out.println(board.getSpaceAt(end.getRowIndex(), end.getColumnIndex()).getPiece());

    Map<Move.MoveStatus, List<Move>> moves = requireMove.getAllMoves();
    List<Move> listMoves = new ArrayList<>();
    Move requiredMove = new Move(new Position(2, 3), new Position(4, 5));
    listMoves.add(requiredMove);
    moves.put(Move.MoveStatus.JUMP, listMoves);
    System.out.println(moves);

    assertEquals(gson.toJson(error("There is still an available jump. You must make this move before you end your turn.")), CuT.handle(request, response));*/
  }




}


