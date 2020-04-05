package com.webcheckers.model;

/**
 * Each space in a row
 *
 * @author Zehra Amena Baig (zab1166)
 */
public class Space
{
  private final boolean isBlack;
  private final int columnIndex;
  private final int rowIndex;
  private Piece piece;

  public Space(int rowIndex, int columnIndex, boolean isBlack, Piece piece)
  {
    this.rowIndex = rowIndex;
    this.columnIndex = columnIndex;
    this.isBlack = isBlack;
    this.piece = piece;
  }

  public Space(int rowIndex, int columnIndex, boolean isBlack)
  {
    this.columnIndex = columnIndex;
    this.isBlack = isBlack;
    this.rowIndex = rowIndex;
  }

  /**
   * Copy constructor
   *
   * @param oldSpace the space to be copied.
   */
  public Space(Space oldSpace)
  {
    this.columnIndex = oldSpace.columnIndex;
    this.rowIndex = oldSpace.rowIndex;
    this.isBlack = oldSpace.isBlack;
    Piece piece = oldSpace.getPiece();
    if (piece != null)
    {
      this.piece = new Piece(piece);
    } else
    {
      this.piece = null;
    }
  }

  /**
   * Returns the row index
   *
   * @return an integer representing the row index
   */
  public int getRowIndex()
  {
    return rowIndex;
  }

  /**
   * Returns index of column
   *
   * @return column index of the space
   */
  public int getColumnIndex()
  {
    return columnIndex;
  }

  /**
   * Returns whether the space is a valid space to place a piece on.
   * True if the space is black b/c can only land on black spaces.
   *
   * @return whether the space is black or not
   */
  public boolean isValidSpace()
  {
    return isBlack;
  }

  /**
   * Returns the piece in the current space
   *
   * @return the piece
   */
  public Piece getPiece()
  {
    return piece;
  }

  public boolean isBlackSpace()
  {
    return isBlack;
  }

  /**
   * Mutator method for piece attribute
   *
   * @param piece the piece to assign to the piece attribute
   */
  public void setPiece(Piece piece)
  {
    this.piece = piece;
  }

  @Override
  public boolean equals(Object o)
  {
    if (o instanceof Space)
    {
      Space sp = (Space) o;
      if ((sp.getColumnIndex() == this.getColumnIndex()) &&
              (sp.isValidSpace() == this.isValidSpace()))
      {
        return sp.getRowIndex() == this.rowIndex;
      }
    }
    return false;
  }


}