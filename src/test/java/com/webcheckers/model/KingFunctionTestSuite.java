package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test that pieces can be kinged by testing the necessary components in model and ui.
 */
public class KingFunctionTestSuite
{
  //Mock attributes
  private Row row;
  private Move move;
  private Board board;

  //Friendly attributes
  private Piece piece = new Piece(Piece.Color.RED, Piece.Type.KING);
  private Position position = new Position(0, 0);
  private Space space = new Space(0,0, true, piece);

  /**
   * Setup the necessary friendly and mock objects to perform test on king piece.
   */
  @BeforeEach
  public void setup()
  {
    row = mock(Row.class);
    move = mock(Move.class);
    board = mock(Board.class);
    when(row.getIndex()).thenReturn(0);
    when(row.getSpaceAt(0)).thenReturn(space);
    when(board.getSpaceAt(0, 0)).thenReturn(space);
  }

  /**
   * Tests all of the model components required to king a piece, the tests of which simulate the interactions of
   * these classes to king a piece.
   */
  @Test
  public void king_Function_Test()
  {
    board.kingPieceAt(position);
    assertEquals(board.getSpaceAt(0,0).getPiece().getType(), Piece.Type.KING);
  }
}
