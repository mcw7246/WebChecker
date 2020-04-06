package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * test the Board class
 *
 * @author Mikayla Wishart 'mcw7246'
 */
public class BoardTest
{

  Board CuT;
  @BeforeEach
  public void setup(){
  }

  @Test
  public void testConstructor_no_variables(){
    CuT = new Board();

    assertEquals(8, CuT.getBoard().size());
  }

  @Test
  public void testGetRowAt(){
    int rowNum = 0;

    CuT = new Board();

    assertNotNull(CuT.getRowAt(rowNum));
  }

  @Test
  public void testGetSpaceAt(){

    CuT = new Board();
    Space testSpace = CuT.getSpaceAt(4, 4);

    assertEquals(testSpace, CuT.getSpaceAt(testSpace.getRowIndex(), testSpace.getColumnIndex()));

  }

  @Test
  public void testKing_piece_at(){
    CuT = new Board();
    int row = 0;
    int col = 1;

    Piece piece = new Piece(Piece.Color.RED, Piece.Type.SINGLE);
    Space testSpace = CuT.getSpaceAt(row, col);
    testSpace.setPiece(null);
    assertNull(testSpace.getPiece());


    testSpace.setPiece(piece);
    //CuT.getSpaceAt(row, col);

    CuT.kingPieceAt(new Position(row, col));

    assertEquals(Piece.Type.KING, CuT.getSpaceAt(row, col).getPiece().getType());

  }

  @Test
  public void testGet_board(){
    CuT = new Board();

    List<Row> board = CuT.getBoard();
    assertEquals(board, CuT.getBoard());
  }


}
