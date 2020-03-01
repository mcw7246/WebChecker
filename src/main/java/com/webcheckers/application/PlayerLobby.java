package com.webcheckers.application;

import com.webcheckers.model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A site-wide holding for all of the player's in the lobby.
 *
 * @author Austin Miller 'akm8654'
 */
public class PlayerLobby
{
    public enum UsernameResult {TAKEN, AVAILABLE, INVALID}

    // Attributes
    private static Map<String, Player> players = new HashMap<>();
    private static Map<String, String> challenges = new HashMap<>();

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
            return true;
        }
    }

    /**
     * Getter for the list of usernames.
     *
     * @return returns the list of usernames
     */
    public synchronized List<String> getUsernames(){
        return new ArrayList<String>(players.keySet());
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
