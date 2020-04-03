package com.webcheckers.model;

import java.util.*;

/**
 *
 * @author Mikayla Wishart 'mcw7246'
 */
import static com.webcheckers.model.Board.DIMENSIONS;

public class BoardView implements Iterable<Row>
{
  // The board itself
  private List<Row> board;
  private boolean flipped = false;

  public BoardView(Board boardActual)
  {
    this.board = new ArrayList<>(boardActual.getBoard());
  }

  /**
   * Updates the board so that the other player is on the bottom.
   */
  public void flip()
  {
    //reverses the board along the x-axis (puts the red at the top and white on the bottom)
    //pieces are now in the white spaces
    Collections.reverse(board);

    //reverses the board along the y-axis(puts the pieces back in the black spaces)
    for(Row r: board){
      Collections.reverse(r.getRow());
    }
    flipped = !flipped;
  }

  /**
   * Get's the abstract stuff.
   *
   * @return the list form of board.
   */
  public List<Row> getBoard()
  {
    return board;
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
