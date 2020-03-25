package com.webcheckers.model;

import org.junit.jupiter.api.*;

import static com.webcheckers.model.Piece.Color.RED;
import static com.webcheckers.model.Piece.Type.SINGLE;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import static org.junit.jupiter.api.Assertions.*;

/**
 * The unit test suite for the {@link Space} component.
 *
 * @author Mario Castano 'mac3186'
 */
@Tag("Model-tier")
class SpaceTest
{

  //Used for getPiece test
  private static final Piece WHITE = new Piece(Piece.Color.WHITE, Piece.Type.SINGLE);

  //Mock attribute
  private Piece piece;

  @BeforeAll
  public void setup() {
    piece = mock(Piece.class);
    when(piece.getColor()).thenReturn(RED);
    when(piece.getType()).thenReturn(SINGLE);
  }

  @Test
  public void testForNull()
  {
    Space CuT = new Space(0, false, WHITE);
    assertNotNull(CuT);
  }

  @Test
  public void getColumnIndex()
  {
    Space CuT = new Space(0, false, WHITE);
    assertEquals(0, CuT.getColumnIndex());
  }

  @Test
  public void isblackSpace() {
    Space CuT = new Space(0, false, WHITE);
    assertFalse(CuT.isBlackSpace());
  }

  @Test
  void isValidSpace()
  {
    Space CuT = new Space(0, false, WHITE);
    assertFalse(CuT.isValidSpace());
  }

  @Test
  void getPiece()
  {
    Space CuT = new Space(0, false, WHITE);
    assertEquals(WHITE, CuT.getPiece());
  }

  @Test
  void setPiece()
  {
    Space CuT = new Space(0, false, WHITE);
    CuT.setPiece(piece);
    assertEquals(piece, CuT.getPiece());
  }

  @Test
  void testEquals()
  {
    Space CuT1 = new Space(0, false, WHITE);
    Space CuT2 = new Space(0, false, WHITE);
    assertEquals(CuT1, CuT2);
  }
}