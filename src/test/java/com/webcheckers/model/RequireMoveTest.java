package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("Model-tier")
public class RequireMoveTest
{

  //Friendly objects
  Board board;
  Piece.Color color;

  /*
  The component under Test (CuT)
   */
  RequireMove CuT;

  @BeforeEach
  public void setup()
  {
    this.board = new Board(); //make a new empty board!
    this.color = Piece.Color.RED;

    CuT = new RequireMove(board, Piece.Color.RED);
  }

  @Test
  public void allMovesCorrectInitialBoard()
  {
    Map<Move.MoveStatus, List<Move>> validMoves = CuT.getAllMoves();
    assertEquals(new ArrayList<>(), validMoves.get(Move.MoveStatus.JUMP));
    assertEquals(7, validMoves.get(Move.MoveStatus.VALID).size());
  }


}
