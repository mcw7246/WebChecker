package com.webcheckers.model;

import com.webcheckers.ui.PostValidateMoveRoute.MoveStatus;

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

  public MoveStatus validateMove(CheckerGame game)
  {
    BoardView board = game.getBoardView();
    Space startSpace = board.getSpaceAt(start.getRow(), start.getCell());
    Space endSpace = board.getSpaceAt(end.getRow(), end.getCell());

    if (!endSpace.isValidSpace())
    {
      return MoveStatus.INVALID_SPACE;
    }
  }
}