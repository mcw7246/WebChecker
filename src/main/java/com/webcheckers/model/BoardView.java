package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The Board itself
 *
 * @author Zehra Amena Baig (zab1166)
 */

public class BoardView implements Iterable<Row>
{
  /**
   * The board's size
   */
  public static final int DIMENSIONS = 8;
  /**
   * The board itself
   */
  private List<Row> board;

  /**
   * enum for the result of a move
   */
  public enum Move
  {
    SINGLE, MULTI, MOVE, INVALID, WIN
  }

  /**
   * BoardView Constructor
   * Initializes the board spaces
   *
   */
  public BoardView()
  {
    board = new ArrayList<Row>();

    for (int i = 0; i < DIMENSIONS; i++)
    {
      board.add(new Row(i));
    }
  }

  @Override
  public Iterator<Row> iterator()
  {
    return board.iterator();
  }

}
