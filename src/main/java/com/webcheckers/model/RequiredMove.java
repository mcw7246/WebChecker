package com.webcheckers.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class used to check for whether or not a player is able to make a move or not.
 * if they are able to make a move then no one has won the game yet
 *
 * @author Mikayla Wishart
 */
public class RequiredMove
{
  public final CheckerGame game;
  public final Board board;
  public final Piece.Color color;

  //map of the spaces that contain pieces that have valid moves(key) and a
  //hash map as the value that contains the end space and the type of move it is
  public Map<Space, Map<Space, Move.MoveStatus>> validMoves = new HashMap<>();

  //map of the endspace of the piece and the type of valid move it is


  public RequiredMove(CheckerGame game, Board board, Piece.Color color){
    this.game = game;
    this.board = board;
    this.color = color;
  }

  public List<Space> getValidMoves(Piece.Color color){
    //creates a new playerPieces that will locate all the pieces on the board
    //for the given color
    PlayerPieces playerPieces = new PlayerPieces(color, board);
    Move.MoveStatus status = null;
    if(playerPieces.getSizeSpaces() != 0){
      List<Space> piecesLocation = playerPieces.getSpaces();
      //loops through all of the pieces on the board of the specific color
      for(Space space: piecesLocation){
        Map<Space, Move.MoveStatus> spaceStatus = validateMoves(space);
        validMoves.put(space, spaceStatus);
      }
    }
    return null;
  }

  public Map<Space, Move.MoveStatus> validateMoves(Space space){

    Map<Space, Move.MoveStatus> typeMove = new HashMap<>();


    Move.MoveStatus testStatus;

    Board testBoard = board;
    //sets the piece to the piece that is passed in
    Piece testPiece = board.getSpaceAt(space.getRowIndex(), space.getColumnIndex()).getPiece();
    Space startSpace = testBoard.getSpaceAt(space.getRowIndex(), space.getColumnIndex());
    Space endSpace;

    //for the white pieces only
    //forward to the right, normal move
    if(space.getRowIndex() + 1 < 8 && space.getColumnIndex() + 1 < 8){
      endSpace = testBoard.getSpaceAt(startSpace.getRowIndex() + 1, startSpace.getColumnIndex() + 1);
      testStatus = normalMove(startSpace, endSpace, color, Piece.Type.KING);
      //add to the map
      if(testStatus == Move.MoveStatus.VALID){
        typeMove.put(endSpace, testStatus);
      }
    }

    //forward to the left, normal move
    if(space.getRowIndex() + 1 < 8 && space.getColumnIndex() - 1 > 0)
    {
      endSpace = testBoard.getSpaceAt(startSpace.getColumnIndex() + 1, startSpace.getColumnIndex() - 1);

      testStatus = normalMove(startSpace, endSpace, color, Piece.Type.KING);
      if (testStatus == Move.MoveStatus.VALID)
      {
        typeMove.put(endSpace, testStatus);
      }
    }

    //backwards to the left, normal moe
    if( space.getRowIndex() - 1 > 0 && space.getColumnIndex() - 1 > 0 ){
      endSpace = testBoard.getSpaceAt(startSpace.getRowIndex() - 1, startSpace.getColumnIndex() - 1);
      testStatus = normalMove(startSpace, endSpace, color, Piece.Type.KING);
      if(testStatus == Move.MoveStatus.VALID){
        typeMove.put(endSpace, testStatus);
      }
    }

    //backwards to the right, normal move
    if (space.getRowIndex() - 1 > 0 && space.getColumnIndex() + 1 < 8){
      endSpace = testBoard.getSpaceAt(space.getRowIndex() - 1, space.getColumnIndex() + 1);
      testStatus = normalMove(startSpace, endSpace, color, Piece.Type.KING);
      if(testStatus == Move.MoveStatus.VALID){
        typeMove.put(endSpace, testStatus);
      }
    }
    return typeMove;
  }

  public Move.MoveStatus normalMove(Space start, Space end, Piece.Color color, Piece.Type type){
    Move testMove = new Move(new Position(start.getRowIndex(), start.getColumnIndex()),
            new Position(end.getRowIndex(), end.getColumnIndex()));

    Move.MoveStatus testStatus = testMove.validateMove(game, start, end);
    return testStatus;
  }
}
