package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.webcheckers.model.Board.DIMENSIONS;

public class BoardView implements Iterable<Row>
{
  // The board itself
  private List<Row> board;
  private boolean flipped = false;

  public BoardView(Board boardActual)
  {
    this.board = boardActual.getBoard();
  }

  /**
   * Updates the board so that the other player is on the bottom.
   */
  public void flip()
  {
    List<Row> flippedBoard = new ArrayList<Row>();
    for (int i = 0; i < DIMENSIONS; i++)
    {
      flippedBoard.add(board.get(DIMENSIONS-1-i));
    }
    this.board = flippedBoard;
    flipped = !flipped;
  }

  /**
   * Returns whether white is on the bottom or not.
   *
   * @return true if white is on the bottom.
   */
  public boolean isFlipped()
  {
    return flipped;
  }

  @Override
  public Iterator<Row> iterator()
  {
    return board.iterator();
  }
}
