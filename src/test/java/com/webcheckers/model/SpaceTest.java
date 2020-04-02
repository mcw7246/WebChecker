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

  @BeforeEach
  public void setup()
  {
    piece = mock(Piece.class);
    when(piece.getColor()).thenReturn(RED);
    when(piece.getType()).thenReturn(SINGLE);
  }

  /**
   * Ensure that the construction of Space works as intended, returning the correct, nonnull data.
   * If this test fails, then all subsequent tests should also fail.
   */
  @Test
  public void testForNull()
  {
    Space CuT = new Space(0, 0, false, WHITE);
    assertNotNull(CuT);
  }

  //Ensure that the getColumnIndex method returns the instantiated space's column value
  @Test
  public void getColumnIndex()
  {
    Space CuT = new Space(0, 0, false, WHITE);
    assertEquals(0, CuT.getColumnIndex());
  }

  /**
   * Ensure that the method correctly returns whether the space is white or black.
   * If this test fails, then the isValidSpace test may also fail if the space's boolean is improperly represented.
   */
  @Test
  public void isblackSpace()
  {
    Space CuT = new Space(0, 0, false, WHITE);
    assertFalse(CuT.isBlackSpace());
  }

  //Ensure that the space is invalid when constructed as a white space, and valid when constructed as a black space.
  @Test
  void isValidSpace()
  {
    Space CuT1 = new Space(0, 0, false, WHITE);
    Space CuT2 = new Space(0, 0, true, WHITE);
    assertFalse(CuT1.isValidSpace());
    assertTrue(CuT2.isValidSpace());
  }

  //Ensure that the piece stored on the space is properly represented.
  @Test
  void getPiece()
  {
    Space CuT = new Space(0, 0, false, WHITE);
    assertEquals(WHITE, CuT.getPiece());
  }

  //Ensure that the piece stored on a space can be updated, removing prior piece's data from the space.
  @Test
  void setPiece()
  {
    Space CuT = new Space(0, 0, false, WHITE);
    CuT.setPiece(piece);
    assertEquals(piece, CuT.getPiece());
  }

  //Ensure that if two spaces have entirely equal fields, that they are considered "equal" by boolean comparison.
  @Test
  void testEquals()
  {
    Space CuT1 = new Space(0, 0, false, WHITE);
    Space CuT2 = new Space(0, 0, false, WHITE);
    assertEquals(CuT1, CuT2);
  }
}