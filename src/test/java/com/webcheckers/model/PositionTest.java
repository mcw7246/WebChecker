package com.webcheckers.model;

import org.junit.jupiter.api.*;

import static com.webcheckers.model.Rules.BOARD_SIZE;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import static org.junit.jupiter.api.Assertions.*;

/**
 * The unit test suite for the {@link Position} component.
 *
 * @author Mario Castano 'mac3186'
 */
@Tag("Model-tier")
public class PositionTest
{
  //mock attributes
  private Rules rule;

  @BeforeEach
  public void setup()
  {
    rule = mock(Rules.class);
  }

  /**
   * Ensure that the construction of Position works as intended, returning the correct, nonnull data.
   * If this test fails, then all subsequent tests should also fail.
   */
  @Test
  public void testForNull()
  {
    Position CuT = new Position(0, 0);
    assertNotNull(CuT);
  }

  /**
   * Ensure that the getRow method correctly returns the value of attribute 'row.'
   */
  @Test
  public void testGetRow()
  {
    Position CuT = new Position(0, 0);
    assertEquals(CuT.getRow(), 0);
  }

  /**
   * Ensure that the getCell method correctly returns the value of attribute 'cell.'
   * If this test or the getRow() test fails, then the indexTooLarge and indexTooSmall tests should fail.
   */
  @Test
  public void testGetCell()
  {
    Position CuT = new Position(0, 0);
    assertEquals(CuT.getCell(), 0);
  }

  /**
   * Ensure that trying to create a position outside the 8x8 grid creates an erroneous piece
   * (-1 replaces row and cell field values).
   */
  @Test
  public void indexInvalidTest()
  {
    Position CuT = new Position(BOARD_SIZE, BOARD_SIZE);
    assertEquals(CuT.getRow(), -1);
    assertEquals(CuT.getCell(), -1);
  }

  /**
   * Ensure that the equals override method works as expected comparing two position objects.
   */
  @Test
  public void testEquals()
  {
    Position CuT1 = new Position(0, 0);
    Position CuT2 = new Position(1, 1);
    Position CuT3 = new Position(0, 0);
    Position CuT4 = null;
    assertTrue(CuT1.equals(CuT3));
    assertFalse(CuT1.equals(CuT2));
    assertFalse(CuT1.equals(CuT4));

  }
}
