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
  public synchronized void newPlayer(Player player)
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

  public boolean challenging(String challenger, String victim){
    if(challenges.containsKey(victim) && challenges.get(victim).equals(challenger) && challengers.contains(challenger)){
      return true;
    }
    else{
      return false;
    }
  }

  public Map<String, String> getGamesChallenge(){return gamesChallenge;}
  public Map<String, String> getGamesVictims(){return gamesVictims; }
  /**
   * Returns the set of all challengers
   *
   * @return challengers in a set of Strings.
   */
  public Set<String> getChallengers()
  {
    return challengers;
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
  public Set<String> getInGame()
  {
    return inGame;
  }

  /**
   * Returns the active checker game for this user.
   *
   * @param user the challenging user.
   * @return the game.
   */
  public CheckerGame getGame(String user)
  {
    return games.get(user);
  }

  public Map<String, CheckerGame> getGames(){return games;}
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
