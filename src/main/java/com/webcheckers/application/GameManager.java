package com.webcheckers.application;

import com.webcheckers.model.Board;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import javafx.util.Pair;

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
  //map where the gameID is the key and the Return is a Pair with the pair of
  // players.
  private Map<Integer, Pair<String, String>> pairs = new HashMap<>();
  //map of the current games going on where the key is the GameID and the value is the CheckerGame associated with it
  private static Map<Integer, CheckerGame> games = new HashMap<>();
  private static Set<String> inGame = new HashSet<>();
  private static PlayerLobby playerLobby;

  public GameManager(PlayerLobby lobby)
  {
    playerLobby = lobby;
  }

  public Set<String> getInGame()
  {
    return inGame;
  }

  public static Map<Integer, CheckerGame> getGames()
  {
    return games;
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
    Pair<String, String> pairToAdd = new Pair<>(challenger, victim);
    this.pairs.put(gameIDNum, pairToAdd);
    games.put(gameIDNum, new CheckerGame(player1, player2, new Board()));
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
   * A move has been made! Update the game.
   *
   * @param id the gameID
   * @param game the new game.
   */
  public void updateGame(int id, CheckerGame game)
  {
    games.replace(id, game);
  }

  /**
   * Returns the pairs of players.
   *
   * @return Pairs which is a Map.
   */
  public Map<Integer, Pair<String, String>> getPairs()
  {
    return pairs;
  }

  /**
   * Finds the pair for the given gameID
   *
   * @param gameID an int of the gameID
   * @return a Pair object with the strings of the victim and challengers.
   */
  public Pair<String, String> getPair(int gameID)
  {
    return pairs.get(gameID);
  }

  /**
   * Gets the player number (As a PLAYERS)
   *
   * @param username the username to check
   * @return A PLAYERS enum.
   */
  public PLAYERS getNumber(String username)
  {
    Player play = playerLobby.getPlayers().get(username);
    int num = play.getPlayerNum();
    if (num == 1)
    {
      return PLAYERS.PLAYER1;
    } else
    {
      return PLAYERS.PLAYER2;
    }
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
    Pair<String, String> pair = pairs.get(id);
    if (pair.getKey().equals(username))
    {
      return playerLobby.getPlayers().get(pair.getValue());
    } else
    {
      return playerLobby.getPlayers().get(pair.getKey());
    }
  }

  public enum PLAYERS
  {PLAYER1, PLAYER2}

}
