package com.webcheckers.model;

/**
 * Wrapper class for model
 *
 * @author Zehra Amena Baig (zab1166)
 */

public class CheckerGame
{
  /**
   * Red Player
   */
  private Player PLAYER_ONE;
  /**
   * White Player
   */
  private Player PLAYER_TWO;
  /**
   * flipped is true if white is on the bottom.
   */
  private boolean flipped = false;

  /**
   * The board
   */
  private BoardView board;
  /**
   * Who's turn it is, 1 or 2.
   */
  private int turn = 1;

  /**
   * Constructor
   */
  public CheckerGame(Player PLAYER_ONE, Player PLAYER_TWO, BoardView board)
  {
    this.PLAYER_ONE = PLAYER_ONE;
    this.PLAYER_TWO = PLAYER_TWO;
    this.board = board;
  }

  /**
   * Returns first (red) player
   *
   * @return the red player
   */
  public Player getRedPlayer()
  {
    return PLAYER_ONE;
  }

  /**
   * Returns second (white) player
   *
   * @return the white player
   */
  public Player getWhitePlayer()
  {
    return PLAYER_TWO;
  }

  /**
   * Returns the board view
   *
   * @return the board view
   */
  public BoardView getBoardView()
  {
    return board;
  }

  /**
   * Returns whether the board is in a "flipped" mode or not.
   *
   * @return if white is on the bottom or not.
   */
  public boolean isFlipped()
  {
    return flipped;
  }

  public BoardView getFlippedBoardView()
  {
    board.flip();
    flipped = !flipped;
    return board;
  }
}
