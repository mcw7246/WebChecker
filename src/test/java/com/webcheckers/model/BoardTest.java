package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * test the Board class
 *
 * @author Mikayla Wishart 'mcw7246'
 */
public class BoardTest
{


  int dimension = 8;
  List<Row> board;

  Board CuT;

  @BeforeEach
  public void setup(){
    board = new ArrayList<>();
  }

  @Test
  public void testConstructor_no_variables(){
    CuT = new Board();

    assertEquals(8, CuT.getBoard().size());
  }


}
