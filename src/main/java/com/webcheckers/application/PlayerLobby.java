package com.webcheckers.application;

import com.webcheckers.model.BoardView;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;

import java.util.*;

/**
 * A site-wide holding for all of the player's in the lobby.
 *
 * @author Austin Miller 'akm8654'
 * @author Mikayla Wishart 'mcw7246'
 */
public class PlayerLobby
{

  // An enum of players, player1 is the challenger and player2 is the victim.
  public enum PLAYERS
  {PLAYER1, PLAYER2}

  ;

  // Attributes
  //Player Hashmap where the username is the key.
  private static Map<String, Player> players = new HashMap<>();
  //A map of all pending challenges, the victim is the key.
  private static Map<String, String> challenges = new HashMap<>();
  //A set full of all people who challenged another
  private static Set<String> challengers = new HashSet<>();
  //A map of all games where the key is the challenger and values are
  // CheckerGames
  private static Map<String, CheckerGame> games = new HashMap<>();
  //A map of all current games where the key is the challenger and values are
  // the victims.
  private static Map<String, String> gamesChallenge = new HashMap<>();
  //A map of all current games where the key is the victim.
  private static Map<String, String> gamesVictims = new HashMap<>();
  //A set of all usernames of players who are actively in a game.
  private static Set<String> inGame = new HashSet<>();

  /**
   * Adds a new player to the lobby.
   *
   * @param player a player object.
   */
  public synchronized static void newPlayer(Player player)
  {
    players.put(player.getUsername(), player);
  }

  /**
   * Adds a challenge to the current hashMap.
   *
   * @param challenger the person challenging
   * @param victim     the person challenged.
   * @return Whether the challenge can be issued, if the victim already is
   * targetted returns false.
   */
  public boolean challenge(String victim, String challenger)
  {
    if (challenges.containsKey(victim))
    {
      return false;
    } else
    {
      challenges.put(victim, challenger);
      challengers.add(challenger);
      return true;
    }
  }

  /**
   * Gets the challenges ongoing in the lobby.
   *
   * @return the map of challengers and victims.
   */
  public Map<String, String> getChallenges()
  {
    return challenges;
  }

  /**
   * Determines whether someone is challenging another or not.
   *
   * @param challenger is the player actively challenging another.
   * @return whether the player is currently challenging or not. (Also false
   * if they are challenging the correct victim
   */
  public boolean challenging(String challenger, String victim)
  {
    if (challenges.get(victim) != null &&
            challenges.get(victim).equals(challenger)){
      return false;
    }
    if (challengers.contains(challenger))
    {
      return true;
    } else
    {
      return false;
    }
  }

  public PLAYERS getNumber(String username)
  {
    if (gamesChallenge.get(username) != null)
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
   * @return the Player object of the opposing player.
   */
  public Player getOpponent(String username)
  {
    String opponent = gamesChallenge.get(username);
    if (opponent != null)
    {
      return players.get(opponent);
    } else
    {
      return players.get(gamesVictims.get(username));
    }
  }

  /**
   * Returns a set of all usernames that are actively in a game.
   *
   * @return the set of usernames actively in a game.
   */
  public static Set<String> getInGame()
  {
    return inGame;
  }

  /**
   * Starts games, where p1 is the person starting the challenge and p2 is the
   * person accepting
   *
   * @param p1 player starting
   * @param p2 player accepting
   */
  public void startGame(String p1, String p2)
  {
    removeChallenger(p2);
    Player player1 = players.get(p1);
    Player player2 = players.get(p2);
    player1.hasEnteredGame();
    player2.hasEnteredGame();
    gamesChallenge.put(p1, p2);
    gamesVictims.put(p2, p1);
    inGame.add(p1);
    inGame.add(p2);
    games.put(p1, new CheckerGame(player1, player2, new BoardView(true)));
  }

  /**
   * Returns the active checker game for this user.
   *
   * @param user the challenging user.
   * @return the game.
   */
  public CheckerGame getGame(String user){
    return games.get(user);
  }

  /**
   * Retrieves a specific player/opponent set from the set of challengers
   */
  public static String getChallenger(String username)
  {
    return gamesChallenge.get(username);
  }

  /**
   * Removes a potential challenger from the list of challengees.
   */
  public static synchronized void removeChallenger(String victim)
  {
    challengers.remove(challenges.get(victim));
    challenges.remove(victim);
  }

  /**
   * Getter for the list of usernames.
   *
   * @return returns the list of usernames
   */
  public synchronized List<String> getUsernames()
  {
    return new ArrayList<>(players.keySet());
  }

  /**
   * Gets the Players hashmap.
   *
   * @return the hashmap of all players.
   */
  public synchronized Map<String, Player> getPlayers()
  {
    return players;
  }

}
