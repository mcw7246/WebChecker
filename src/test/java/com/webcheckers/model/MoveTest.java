package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit test for the {@link Move} componenet.
 *
 * @author Austin Miller 'akm8654'
 */
@Tag("Model-tier")
public class MoveTest
{

  /**
   * Component under test, a stateless component that tests the functionality
   * of the {@link Move}
   */
  private Move CuT;

  //Updated objects to represent start and ending indices.
  int startRow = 0;
  int startCell = 0;
  int endRow = 0;
  int endCell = 0;

  //Friendly Objects
  Position start;
  Position end;
  Piece piece;

  // Mocked objects.
  CheckerGame game;
  Board board;
  Space startSpace;
  Space endSpace;

  @BeforeEach
  public void setup()
  {
    game = mock(CheckerGame.class);
    board = mock(Board.class);
    startSpace = mock(Space.class);
    endSpace = mock(Space.class);
    piece = new Piece(Piece.Color.RED);
    when(game.getBoard()).thenReturn(board);
    when(board.getSpaceAt(startRow, startCell)).thenReturn(startSpace);
    when(board.getSpaceAt(endRow, endCell)).thenReturn(endSpace);
    when(startSpace.getPiece()).thenReturn(piece);
  }

  @Test
  public void correct_move()
  {
  }
}
