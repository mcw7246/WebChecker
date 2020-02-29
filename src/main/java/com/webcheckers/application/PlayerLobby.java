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

    // Constants
    static final String USERNAME_TAKEN = "This username is already taken. Please choose another one.";
    static final String USERNAME_INVALID = "This username is invalid. It contains characters that are not allowed in a username.";

    // Attributes
    private static Map<String, Player> players = new HashMap<>();

    // Constructor
    public PlayerLobby(){

    }

    public synchronized static void newPlayer(Player player){
        players.put(player.getUsername(), player);
    }

    /**
     * Determines whether the lobby has more than one player in it or not.
     *
     * @return whether usernames has more than one player or not.
     */
    public boolean hasOpponents(){
        return (players.size() > 1);
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
