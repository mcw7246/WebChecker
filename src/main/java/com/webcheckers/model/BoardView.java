package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The Board itself
 *
 * @author Zehra Amena Baig (zab1166)
 * @author Austin Miller (akm8654)
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
   * @param isFirst holds whether player is 1st (red) or not
   */
  public BoardView(boolean isFirst)
  {
    board = new ArrayList<Row>();

    for (int i = 0; i < DIMENSIONS; i++)
    {
      board.add(new Row(i, isFirst));
    }
  }

  /**
   * returns the board list.
   *
   * @return a list of rows.
   */
  public List<Row> getBoard(){
    return this.board;
  }

  /**
   * A helper function to find a space at the given indices.
   *
   * @param rowInt the row index
   * @param cellInt the col (or cell) index
   * @return a Space requested.
   */
  public Space getSpaceAt(int rowInt, int cellInt)
  {
    Row row = board.get(rowInt);
    return row.getSpaceAt(cellInt);
  }

  @Override
  public Iterator<Row> iterator()
  {
    return board.iterator();
  }

}
