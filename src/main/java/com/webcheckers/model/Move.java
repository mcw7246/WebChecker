package com.webcheckers.model;

/**
 * Uses a getStart and getEnd for a position model.
 *
 * @author Austin Miller 'akm8654'
 */
public class Move
{
  private Position start;
  private Position end;

  public Move(Position start, Position end)
  {
    this.start = start;
    this.end = end;
  }

  /**
   * Gets the starting position for the move.
   *
   * @return a position object.
   */
  public Position getStart()
  {
    return start;
  }

  /**
   * Gets the ending position for the given move.
   *
   * @return the ending position for the end.
   */
  public Position getEnd()
  {
    return end;
  }
}