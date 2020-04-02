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
   * The board
   */
  private Board board;

  /**
   * Who's turn it is, 1 or 2.
   */
  private int turn = 1;

  /**
   * Constructor
   */
  public CheckerGame(Player PLAYER_ONE, Player PLAYER_TWO, Board board)
  {
    this.PLAYER_ONE = PLAYER_ONE;
    this.PLAYER_TWO = PLAYER_TWO;
    this.board = board;
  }

  /**
   * Get whose turn it is to go.
   *
   * @return the int of players turn.
   */
  public String getTurn()
  {
    if (turn == 1)
    {
      return PLAYER_ONE.getUsername();
    } else
    {
      return PLAYER_TWO.getUsername();
    }
  }

  /**
   * Get active color
   *
   * @return the piece color of red or white if the turn is 1 or 2 respectively.
   */
  public Piece.Color getColor()
  {
    if(turn == 1)
    {
      return Piece.Color.RED;
    } else {
      return Piece.Color.WHITE;
    }
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

  /** Makes a move for the game
   *
   * @param move the move to be made
   */
  public void makeMove(Move move)
  {
    Position start = move.getStart();
    Position end = move.getEnd();

    Piece piece = board.getSpaceAt(start.getRow(), start.getCell()).getPiece();
    board.getSpaceAt(end.getRow(), end.getCell()).setPiece(piece);
    board.getSpaceAt(start.getRow(), start.getCell()).setPiece(null);
  }

  /**
   * Switches the turn between 2 and 1.
   */
  public void updateTurn()
  {
    turn = (turn % 2) + 1; //Switches between 2 and 1.
  }

  /** A move has been made! Update the board!
   *
   * @param board the new board.
   */
  public void updateBoard(Board board)
  {
    this.board = board;
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
   * Gets the actual board (not just the view)
   *
   * @return the board.
   */
  public Board getBoard()
  {
    return board;
  }

  /**
   * Returns the board view
   *
   * @return the board view
   */
  public BoardView getBoardView(boolean flipped)
  {
    BoardView view = new BoardView(board);
    if (flipped)
    {
      view.flip();
    }
    return view;
  }
}
