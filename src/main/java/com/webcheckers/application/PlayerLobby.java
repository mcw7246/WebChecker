package com.webcheckers.application;

import com.webcheckers.model.Player;

import java.util.*;

/**
 * A site-wide holding for all of the player's in the lobby.
 *
 * @author Austin Miller 'akm8654' Mikayla Wishart 'mcw7246'
 */
public class PlayerLobby
{

    // Attributes
    private static Map<String, Player> players = new HashMap<>();
    private static Map<String, String> challenges = new HashMap<>();
    private static Set<String> challengers = new HashSet<>();

    // Constructor
    public PlayerLobby()
    {
    }

    public synchronized static void newPlayer(Player player)
    {
        players.put(player.getUsername(), player);
    }

    /**
     * Adds a challenge to the current hashMap.
     *
     * @param challenger the person challenging
     * @param victim the person challenged.
     * @return Whether the challenge can be issued, if the victim already is
     * targetted returns false.
     */
    public boolean challenge(String victim, String challenger){
        if(challenges.containsKey(victim)){
            return false;
        } else {
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
  public static Map<String, String> getChallenges()
  {
    return challenges;
  }

  /**
   * Determines whether someone is challenging another or not.
   *
   * @param challenger is the player actively challenging another.
   * @return whether the player is currently challenging or not.
   */
    public boolean challenging(String challenger)
    {
      if(challengers.contains(challenger)){
        return true;
      } else {
        return false;
      }
    }

    /**
     * Removes a potential challenger from the list of challengees.
     */
    public void removeChallenger(String challenger) {
        if (challengers.contains(challenger)) {
            challengers.remove(challenger);
        }
    }

    /**
     * Getter for the list of usernames.
     *
     * @return returns the list of usernames
     */
    public synchronized List<String> getUsernames(){
        return new ArrayList<>(players.keySet());
    }

    /**
     * Gets the Players hashmap.
     *
     * @return the hashmap of all players.
     */
    public synchronized Map<String, Player> getPlayers(){
        return players;
    }

}
