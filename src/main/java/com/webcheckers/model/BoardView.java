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
  public BoardView()
  {
    board = new ArrayList<Row>();

    for (int i = 0; i < DIMENSIONS; i++)
    {
      board.add(new Row(i));
    }
  }

  /**
   * Updates the board so that the other player is on the bottom.
   */
  public void flip()
  {
    List<Row> flippedBoard = new ArrayList<Row>();

    for (int i = 0; i < DIMENSIONS; i++)
    {
      flippedBoard.add(board.get(DIMENSIONS-i));
    }

    this.board = flippedBoard;

  }

  @Override
  public Iterator<Row> iterator()
  {
    return board.iterator();
  }

}
