package com.webcheckers.model;

/**
 * Each space in a row
 *
 * @author Zehra Amena Baig (zab1166)
 */
public class Space
{
  private final boolean isblackSpace;
  private final int columnIndex;
  private Piece piece;

  public Space(int columnIndex, boolean isblackSpace, Piece piece)
  {
    this.columnIndex = columnIndex;
    this.isblackSpace = isblackSpace;
    this.piece = piece;
  }

  public Space(int columnIndex, boolean isblackSpace)
  {
    this.columnIndex = columnIndex;
    this.isblackSpace = isblackSpace;
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
    return isblackSpace;
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

  /**
   * Mutator method for piece attribute
   *
   * @param piece the piece to assign to the piece attribute
   */
  public void setPiece(Piece piece)
  {
    this.piece = piece;
  }

  public boolean equals(Object o)
  {
    if(o instanceof Space)
    {
      Space sp = (Space) o;
      if((sp.getColumnIndex() == this.getColumnIndex()) && (sp.isValidSpace() == this.isValidSpace()))
      {
        if(this.isValidSpace() == false)
        {
          return true;
        }
        else
        {
          if((sp.getPiece().getColor() == this.getPiece().getColor()) && (sp.getPiece().getType() == this.getPiece().getType()))
          {
            return true;
          }
          else
          {
            return false;
          }
        }
      }
    }
    return false;
  }


}