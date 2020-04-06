package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit test for the {@link Move} componenet.
 *
 * @author Austin Miller 'akm8654'
 * @author Mikayla Wishart 'mcw7246'
 */
@Tag("Model-tier")
public class MoveTest
{

  /**
   * Component under test, a stateless component that tests the functionality
   * of the {@link Move}
   */
  private Move CuT;

  //Updated objects to represent start and ending indices.
  int startRow = 0;
  int startCell = 0;
  int endRow = 0;
  int endCell = 0;
  int rowDiff;
  int colDiff;

  //Friendly Objects
  Position start;
  Position end;
  Piece redPiece;
  Piece whitePiece;
  Space startSpace;
  Space endSpace;
  Move.MoveStatus status;

  // Mocked objects.
  CheckerGame game;
  Board board;
  Move move;

  @BeforeEach
  public void setup()
  {
    game = mock(CheckerGame.class);
    board = new Board();
    //startSpace = mock(Space.class);
    //endSpace = mock(Space.class);
    redPiece = new Piece(Piece.Color.RED);
    whitePiece = new Piece(Piece.Color.WHITE);
    move = mock(Move.class);
    when(move.getStart()).thenReturn(start);
    when(move.getEnd()).thenReturn(end);
    when(game.getBoard()).thenReturn(board);
    //when(board.getSpaceAt(startRow, startCell)).thenReturn(startSpace);
    //when(board.getSpaceAt(endRow, endCell)).thenReturn(endSpace);
    //when(startSpace.getPiece()).thenReturn(piece);
  }

  //tests the getStart() method
  @Test
  public void testGet_start(){
    start = new Position(1,1);
    end = new Position(2,2);

    CuT = new Move(start, end);

    assertEquals(start, CuT.getStart());
  }

  //tests the getEnd() method
  @Test
  public void testGet_end(){
    start = new Position(1, 1);
    end = new Position(2, 2);

    CuT = new Move(start, end);

    assertEquals(end, CuT.getEnd());

  }


  //tests
  @Test
  public void testInvalid_space(){
    start = new Position(0, 1);
    end = new Position(0,  0);
    startSpace = new Space(0, 1, true, redPiece);
    endSpace = new Space(0, 0, false, redPiece);

    CuT = new Move(start, end);

    status = CuT.validateMove(game, startSpace, endSpace);

    assertEquals(Move.MoveStatus.INVALID_SPACE, status);
  }

  @Test
  public void testSame_Space(){
    //-------------------------WORKING----------------------------------------
    start = new Position(0, 1);
    end = new Position(0, 1);
    startSpace = new Space(0, 1, true, redPiece);
    endSpace = new Space(0, 1, true, redPiece);

    CuT = new Move(start, end);

    status = CuT.validateMove(game, startSpace, endSpace);

    assertEquals(startSpace, endSpace);
    assertEquals(Move.MoveStatus.SAME_SPACE, status);

  }

  @Test
  public void testOccupied_space(){
    //------------------------WORKING--------------------------------------------
    start = new Position(0, 1);
    end = new Position(1, 0);
    Piece testPiece = new Piece(Piece.Color.WHITE, Piece.Type.SINGLE);

    startSpace = new Space(0, 1, true, whitePiece);
    endSpace = new Space(1, 0, true, testPiece);

    endSpace.setPiece(testPiece);
    //TODO fix the error (something inside the Move class

    CuT = new Move(start, end);

    status = CuT.validateMove(game, startSpace, endSpace);
    assertNotNull(endSpace.getPiece());
    assertEquals(Move.MoveStatus.OCCUPIED, CuT.validateMove(game, startSpace, endSpace));

  }

  @Test
  public void testValidate_Too_Far(){
    //---------------------WORKING--------------------------------
    start = new Position(2, 3);
    end = new Position(4,1);
    rowDiff = end.getRow() - start.getRow();
    colDiff = end.getCell()- start.getCell();
    Space middleSpace = board.getSpaceAt(start.getRow() + (rowDiff/2), start.getCell() + (colDiff/2));
    startSpace = new Space(0, 1, true, whitePiece);
    endSpace = new Space(4,5, true, null );
    assertNull(endSpace.getPiece());

    assertNull(middleSpace.getPiece());

    CuT = new Move(start, end);

    status = CuT.validateMove(game, startSpace, endSpace);
    assertEquals(Move.MoveStatus.TOO_FAR, status);
  }

  @Test
  public void testValidate_jump_own_piece(){
    //--------------------------WORKING----------------------------------
    start = new Position(0, 1);
    end = new Position(2, 3);
    Piece testPiece = new Piece(Piece.Color.WHITE, Piece.Type.SINGLE);

    Space middleSpace = new Space(1, 2, true, testPiece);
    startSpace = new Space(0, 1, true, whitePiece);
    endSpace = new Space(2, 3, true);

    assertNull(endSpace.getPiece());
    assertNotNull(middleSpace.getPiece());

    CuT = new Move(start, end);
    status = CuT.validateMove(game, startSpace, endSpace);

    assertEquals(Move.MoveStatus.JUMP_OWN, status);
  }


  @Test
  public void testJump(){
    start = new Position(3, 0);
    end = new Position(5, 2);

    rowDiff = end.getRow() - start.getRow();
    colDiff = end.getCell() - start.getCell();

    Space middleSpace = board.getSpaceAt(4, 1);
    middleSpace.setPiece(redPiece);
    startSpace = new Space(start.getRow(), start.getCell(), true, whitePiece);
    endSpace = new Space(end.getRow(), end.getCell(), true);

    assertNotNull(middleSpace.getPiece());
    CuT = new Move(start, end);
    status = CuT.validateMove(game, startSpace, endSpace);

    assertEquals(Move.MoveStatus.JUMP, status);
  }

  @Test
  public void testInvalid_backwards(){
    start = new Position(5, 2);
    end = new Position(3, 0);

    rowDiff = end.getRow() - start.getRow();
    colDiff = end.getCell() - start.getCell();

    Space middleSpace = board.getSpaceAt(start.getRow() + (rowDiff/2), start.getCell() + (colDiff/2));
    middleSpace.setPiece(redPiece);
    startSpace = new Space(start.getRow(), start.getCell(), true, whitePiece);
    endSpace = new Space(end.getRow(), end.getCell(), true);

    assertNotNull(middleSpace.getPiece());
    CuT = new Move(start, end);
    status = CuT.validateMove(game, startSpace, endSpace);

    System.out.println(status);

    assertEquals(Move.MoveStatus.INVALID_BACKWARDS, status);
  }

  @Test
  public void testValid_King(){
    start = new Position(5, 2);
    end = new Position(3, 0);

    rowDiff = end.getRow() - start.getRow();
    colDiff = end.getCell() - start.getCell();

    Space middleSpace = board.getSpaceAt(start.getRow() + (rowDiff/2), start.getCell() + (colDiff/2));
    middleSpace.setPiece(redPiece);
    whitePiece.setType(Piece.Type.KING);
    startSpace = new Space(start.getRow(), start.getCell(), true, whitePiece);
    endSpace = new Space(end.getRow(), end.getCell(), true);

    CuT = new Move(start, end);
    status = CuT.validateMove(game, startSpace, endSpace);
    assertEquals(Move.MoveStatus.JUMP, status);
  }
}
