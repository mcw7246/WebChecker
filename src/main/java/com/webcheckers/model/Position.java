package com.webcheckers.model;

import static com.webcheckers.model.Rules.BOARD_SIZE;

/**
 * Holds the position of a piece.
 */
public class Position
{
  private int row; // from 0 to 7
  private int cell; //col from 0 to 7
  private static final int MAX_ROW = BOARD_SIZE - 1;
  private static final int MAX_CELL = BOARD_SIZE - 1;

  /**
   * Creates a position object using a given row and cell, if they are out of
   * bounds then
   *
   * @param row  the row index of the position.
   * @param cell the cell index of the position.
   */
  public Position(int row, int cell)
  {
    if (row > MAX_ROW)
    {
      this.row = -1; //error
    } else
    {
      this.row = row;
    }

    if (cell > MAX_CELL)
    {
      this.cell = -1;
    } else
    {
      this.cell = cell;
    }
  }

  /**
   * Gets the row.
   *
   * @return the row index for the position
   */
  public int getRow()
  {
    return row;
  }

  /**
   * Gets the column index for the position.
   *
   * @return an int representing the column. (0-based)
   */
  public int getCell()
  {
    return cell;
  }
}
