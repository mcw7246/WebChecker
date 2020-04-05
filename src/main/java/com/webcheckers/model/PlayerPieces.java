package com.webcheckers.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that gets all of the pieces on the board for a given player based
 * on the color of their pieces
 *
 * @author Mikayla Wishart
 */
public class PlayerPieces
{
  private final Piece.Color color;
  private Board board;

  private List<Space> spaces = new ArrayList<>();

  public PlayerPieces(Piece.Color color, Board board)
  {
    this.color = color;
    this.board = board;
  }

  //used to find the pieces that are left on the board
  public void findPieces()
  {
    for(Row row: board)
    {
      for(Space space: row)
      {
        if(space.getPiece() != null && space.getPiece().getColor() == color)
        {
          spaces.add(space);
        }
      }
    }
  }

  //returns the list of spaces that contain the player's pieces
  public List<Space> getSpaces()
  {
    findPieces();
    return spaces;
  }

  //used to get the number of pieces that the player has left
  public int getSizeSpaces()
  {
    return spaces.size();
  }

}
