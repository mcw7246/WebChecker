package com.webcheckers.application;

import com.sun.javafx.image.IntPixelGetter;
import com.webcheckers.model.Board;
import com.webcheckers.model.Move;

import java.util.*;

public class ReplayManager
{
  private Map<Integer, List<Board>> gameMoves = new HashMap<>();
  private Set<Integer> pastGames = new HashSet<>();

  /**
   * adds a move to the list for whatever board.
   *
   * @param gameID the game ID to get
   * @param board the board to add
   */
  public void addMove(int gameID, Board board)
  {
    if(gameMoves.containsKey(gameID))
    {
      List<Board> currentList = gameMoves.get(gameID);
      currentList.add(board);
      gameMoves.replace(gameID, currentList);
    } else {
      List<Board> currentList = new ArrayList<>();
      currentList.add(board);
      gameMoves.put(gameID, currentList);
    }
  }

  /**
   * Returns the board at the current move value.
   *
   * @param gameID the game to get
   * @param move the move value to have
   * @return the Board at the given move index.
   */
  public Board getGameAtMove(int gameID, int move)
  {
    List<Board> currentList = gameMoves.get(gameID);
    if (currentList != null){
      return currentList.get(move);
    } else {
      return null;
    }
  }

  /**
   * Ending the game with the given id.
   *
   * @param gameID the id to end.
   */
  public void endGame(int gameID)
  {
    pastGames.add(gameID);
  }

  /**
   * Returns all of the games that have ended.
   *
   * @return the list of all games that are over.
   */
  public List<Integer> getGameOverIDs()
  {
    List<Integer> list = new ArrayList<>();
    Object[] array = pastGames.toArray();
    Integer[] intArray = new Integer[array.length];
    System.arraycopy(array, 0, intArray, 0, array.length);
    Collections.addAll(list, intArray);
    return list;
  }
}
