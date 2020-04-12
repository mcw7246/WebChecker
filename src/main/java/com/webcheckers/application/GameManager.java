package com.webcheckers.application;

import com.google.gson.Gson;
import com.webcheckers.model.Board;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Holds and manages all games once they start
 *
 * @author Austin Miller 'akm8654'
 * @author Mikayla Wishart 'mcw7246'
 */
public class GameManager
{

  private static Map<String, Integer> gameID = new HashMap<>();
  private static int gameIDNum = 1;
  //map where the gameID is the key and the Return is a HashMap with the pair of
  // players.
  private Map<Integer, HashMap<String, String>> pairs = new HashMap<>();
  //map of the current games going on where the key is the GameID and the value is the CheckerGame associated with it
  private static Map<Integer, CheckerGame> games = new HashMap<>();
  private static Set<String> inGame = new HashSet<>();
  private static Map<String, CheckerGame> clientSideGames = new HashMap<>();
  private static PlayerLobby playerLobby;

  public GameManager(PlayerLobby lobby)
  {
    playerLobby = lobby;
  }

  public Set<String> getInGame()
  {
    return inGame;
  }

  /**
   * Starts games, where p1 is the person starting the challenge and p2 is the
   * person accepting
   *
   * @param challenger player starting (challenger)
   * @param victim     player accepting (victim)
   */
  public void startGame(String challenger, String victim)
  {
    playerLobby.removeChallenger(challenger);
    Player player1 = playerLobby.getPlayers().get(challenger);
    Player player2 = playerLobby.getPlayers().get(victim);
    player2.makePlayer2();
    player1.hasEnteredGame();
    player2.hasEnteredGame();
    inGame.add(challenger);
    inGame.add(victim);
    gameIDNum += 1;
    gameID.put(challenger, gameIDNum);
    gameID.put(victim, gameIDNum);
    HashMap<String, String> pairToAdd = new HashMap<>();
    pairToAdd.put(challenger, victim);
    this.pairs.put(gameIDNum, pairToAdd);
    games.put(gameIDNum, new CheckerGame(player1, player2, new Board()));
  }

  /**
   * Just starts a test game
   *
   * @param challenger the name of the challenger (most likely player1)
   * @param victim the name of the victim (most likely player2)
   * @param filename the name of the file that holds the test board.
   */
  public void startTestGame(String challenger, String victim, String filename
          , int turn)
  {
    playerLobby.removeChallenger(challenger);
    Player player1 = playerLobby.getPlayers().get(challenger);
    Player player2 = playerLobby.getPlayers().get(victim);
    player2.makePlayer2();
    player1.hasEnteredGame();
    player2.hasEnteredGame();
    inGame.add(challenger);
    inGame.add(victim);
    gameIDNum += 1;
    gameID.put(challenger, gameIDNum);
    gameID.put(victim, gameIDNum);
    HashMap<String, String> pairToAdd = new HashMap<>();
    pairToAdd.put(challenger, victim);
    this.pairs.put(gameIDNum, pairToAdd);
    Gson gson = new Gson();
    Board board;
    try
    {
      board = gson.fromJson(new FileReader(filename), Board.class);
    } catch (FileNotFoundException e) {
      System.err.println("ERROR: FILE NOT FOUND STARTING GAME FROM SCRATCH");
      board = new Board();
    }
    CheckerGame game = new CheckerGame(player1, player2, board);
    if(turn > 1)
    {
      game.updateTurn();
    }
    games.put(gameIDNum, game);
  }

  /**
   * Returns the game id from a username
   *
   * @param username: the username to get the Game ID
   * @return the gameID number.
   */
  public int getGameID(String username)
  {
    return gameID.get(username);
  }

  /**
   * Returns the game of the currentGameID
   *
   * @param currentGameID the id to check
   * @return the CheckerGame at that Id.
   */
  public CheckerGame getGame(int currentGameID)
  {
    return games.get(currentGameID);
  }

  /**
   * Holds all the beginning client games do that only one person has a copy
   * of it.
   *
   * @param gameID   the unique gameID of the game being played
   * @param username the client that needs to hold the game
   * @return the checkerGame copy.
   */
  public CheckerGame makeClientSideGame(int gameID, String username)
  {
    if (clientSideGames.containsKey(username))
    {
      removeClientSideGame(username);
    }
    CheckerGame serverGame = new CheckerGame(getGame(gameID));
    clientSideGames.put(username, serverGame);
    return clientSideGames.get(username);
  }

  /**
   * Removes the client side game
   *
   * @param username: the key to be removed.
   */
  public void removeClientSideGame(String username)
  {
    clientSideGames.remove(username);
  }

  /**
   * Gets the local game for the designated player.
   *
   * @param username the players name that has the game.
   * @return the client side CheckerGame.
   */
  public CheckerGame getLocalGame(String username)
  {
    return clientSideGames.get(username);
  }

  /**
   * A move has been made! Update the game.
   *
   * @param id   the gameID
   * @param game the new game.
   */
  public void updateGame(int id, CheckerGame game)
  {
    game.setMoved(false);
    games.replace(id, game);
  }

  /**
   * Finds the pair for the given gameID
   *
   * @param gameID an int of the gameID
   * @return a Pair object with the strings of the victim and challengers.
   */
  public HashMap<String, String> getPair(int gameID)
  {
    return pairs.get(gameID);
  }

  /**
   * Returns the opponent of a player.
   *
   * @param username the username of the player
   * @return the opponent as a Player Object.
   */
  public Player getOpponent(String username)
  {
    int id = getGameID(username);
    HashMap<String, String> pair = pairs.get(id);
    //if the
    if (pair.containsKey(username))
    {
      return playerLobby.getPlayers().get(pair.get(username));
    } else
    {
      for (String key : pair.keySet())
      {
        if (pair.get(key).equals(username))
        {
          return playerLobby.getPlayers().get(key);
        }
      }
      return null;
    }
  }

}
