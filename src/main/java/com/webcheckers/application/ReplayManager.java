package com.webcheckers.application;

import com.sun.javafx.image.IntPixelGetter;
import com.webcheckers.model.Board;
import com.webcheckers.model.Move;

import java.util.*;

public class ReplayManager
{
  private Map<Integer, List<Board>> pastGames = new HashMap<>();

  /**
   * adds a move to the list for whatever board.
   *
   * @param gameID the game ID to get
   * @param board the board to add
   */
  public void addMove(int gameID, Board board)
  {
    if(pastGames.containsKey(gameID))
    {
      List<Board> currentList = pastGames.get(gameID);
      currentList.add(board);
      pastGames.replace(gameID, currentList);
    } else {
      List<Board> currentList = new ArrayList<>();
      currentList.add(board);
      pastGames.put(gameID, currentList);
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
    List<Board> currentList = pastGames.get(gameID);
    if (currentList != null){
      return currentList.get(move);
    } else {
      return null;
    }
  }

  public List<Integer> getGameOverIDs()
  {
    List<Integer> list = new ArrayList<>();
    Object[] array = pastGames.keySet().toArray();
    Integer[] intArray = new Integer[array.length];
    System.arraycopy(array, 0, intArray, 0, array.length);
    Collections.addAll(list, intArray);
    return list;
  }
}
