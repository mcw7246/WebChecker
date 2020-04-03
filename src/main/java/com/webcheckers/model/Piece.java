package com.webcheckers.model;

/**
 * Each piece on the board
 *
 * @author Zehra Amena Baig (zab1166)
 */

public class Piece
{



  /**
   * The color of the Piece
   */
  public enum Color
  {
    RED, WHITE
  }

  /**
   * The type of piece
   */
  public enum Type
  {
    SINGLE, KING
  }

  /**
   * The color of the current piece
   */
  private Color color;
  /**
   * The type of the current piece
   */
  private Type type;

  /**
   * Constructor for Piece class
   *
   * @param color the color of the current piece
   * @param type  the type of the current piece
   */
  public Piece(Color color, Type type)
  {
    this.color = color;
    this.type = type;
  }

  public Piece(Color color)
  {
    this.color = color;
    this.type = Type.SINGLE;
  }

  /**
   * A copy constructor for piece.
   *
   * @param piece the piece to be copied.
   */
  public Piece(Piece piece)
  {
    this.color = piece.color;
    this.type = piece.type;
  }

  /**
   * Returns the color
   *
   * @return the color of the current piece
   */
  public Color getColor()
  {
    return this.color;
  }

  /**
   * Returns the type (Single or King)
   *
   * @return the type of the current piece
   */
  public Type getType()
  {
    return this.type;
  }


}
