package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The Board itself, the abstract copy that the Game Manager Holds by itself,
 * as opposed to BoardView which is submitted to the player each time requested.
 *
 * @author Zehra Amena Baig (zab1166)
 */

public class Board implements Iterable<Row>
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
   * Helper method to get the space at a specific row/cell
   *
   * @param row the row index
   * @param cell the col index
   * @return the space located at the given values.
   */
  public Space getSpaceAt(int row, int cell)
  {
    return board.get(row).getSpaceAt(cell);
  }

  /**
   * BoardView Constructor
   * Initializes the board spaces
   */
  public Board()
  {
    board = new ArrayList<Row>();

    for (int i = 0; i < DIMENSIONS; i++)
    {
      board.add(new Row(i));
    }
  }

  public Board(List<Row> board)
  {
    this.board = board;
  }

  /**
   * Gives a copy of the board.
   *
   * @return the board.
   */
  public List<Row> getBoard()
  {
    return board;
  }

  @Override
  public Iterator<Row> iterator()
  {
    return board.iterator();
  }

}
