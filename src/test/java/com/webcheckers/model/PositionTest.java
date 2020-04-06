package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test for the Position class {@link Position}
 * @author Mikayla Wishart 'mcw7246'
 */
@Tag("Model-tier")
public class PositionTest
{
  Position CuT;

  private int row;
  private int column;

  private int max = 7;

  @BeforeEach
  public void setup(){
    row = 0;
    column = 1;
  }
  @Test
  public void ctor_withArg(){
    CuT = new Position(row, column);

    assertTrue(max > row);
    assertTrue(max >  column);
  }

  @Test
  public void testRow_too_big(){
    CuT = new Position(row + 8, column);

    assertEquals(-1, CuT.getRow());
  }

  @Test
  public void testCol_too_big(){
    CuT = new Position(row, column + 8);

    assertEquals(-1, CuT.getCell());
  }

  @Test
  public void testGetRow(){
    CuT = new Position(row, column);

    assertEquals(0, CuT.getRow());
  }

  @Test
  public void testGetColumn(){
    CuT = new Position(row, column);

    assertEquals(1, CuT.getCell());
  }
}
