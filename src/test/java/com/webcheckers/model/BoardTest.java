package com.webcheckers.model;

import org.junit.jupiter.api.*;

import static com.webcheckers.model.Piece.Color.RED;
import static com.webcheckers.model.Piece.Type.SINGLE;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The unit test suite for the {@link Board} component.
 *
 * @author Mario Castano 'mac3186'
 */
@Tag("Model-tier")
public class BoardTest
{
  //Mock attributes
  private Space space;
  private Row row;

  //Friendly attributes
  private Position position = new Position(0, 0);
  private Piece piece = new Piece(Piece.Color.WHITE, Piece.Type.SINGLE);

  @BeforeEach
  public void setup()
  {
    row = mock(Row.class);
    space = mock(Space.class);
    when(row.getIndex()).thenReturn(0);
    when(row.getSpaceAt(0)).thenReturn(space);
    when(space.getRowIndex()).thenReturn(0);
    when(space.getColumnIndex()).thenReturn(0);
    when(space.getPiece()).thenReturn(piece);
  }

  /**
   * Ensure that the construction of a Board works as intended, not returning null.
   * If this test fails, so too should all other tests.
   */
  @Test
  public void testForNull()
  {
    Board CuT = new Board();
    assertNotNull(CuT);
  }

  /**
   * Ensure that the constructor using another Board object works as expected, not returning a Null object.
   * If this test fails, all subsequent tests are assumed failed.
   */
  @Test
  public void testForNullUsingBoard()
  {
    Board CuT1 = new Board();
    Board CuT2 = new Board(CuT1);
    assertNotNull(CuT2);
  }

  /**
   * Ensure that the getRowAt() method works as intended, returning a row on the board from a given index.
   */
  @Test
  public void getRowAtTest()
  {
    Board CuT = new Board();
    assertEquals(row.getIndex(), CuT.getRowAt(0).getIndex());
  }

  /**
   * Ensure that the getSpaceAt() method correctly returns a space object from the board by coordinate index.
   */
  @Test
  public void getSpaceAtTest()
  {
    Board CuT = new Board();
    assertNotNull(CuT.getSpaceAt(0,0));
    assertEquals(CuT.getSpaceAt(0,0), space);
  }

  /**
   * Ensure that the kingPieceAt() method correctly crowns a designated piece on a space on the board.
   * Unlike other tests, this one requires a friendly Space attribute to work.
   */
  @Test
  public void kingPieceAtTest()
  {
    Board CuT = new Board();
    CuT.getSpaceAt(0,0).setPiece(piece);
    CuT.kingPieceAt(position);
    assertEquals(CuT.getSpaceAt(0,0).getPiece().getType(), Piece.Type.KING);
  }
}
