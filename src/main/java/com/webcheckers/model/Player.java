package com.webcheckers.model;

import com.webcheckers.application.PlayerLobby;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * placeholder for actual code and information
 *
 * swen fwiends
 */

public class Player {


    public static UsernameResult result = null;
    private String username;
    private PlayerLobby playerLobby;

    public Player(PlayerLobby playerLobby){
        this.playerLobby = playerLobby;
    }

    public String setUsername(String name)
    {
        username = name;
        return username;
    }
    //constructor: saves the username that the player wants\

    //checks if username is valid
    public synchronized UsernameResult isValidUsername(String username)
    {

        //make sure that the username contains letters and numbers and spaces only
        boolean userContains = Pattern.matches("[a-zA-Z0-9]+", username);
        if(!userContains)
        {
            result = UsernameResult.INVALID;
            return result;
        }
        //if the arrayList.size() == 0 then there are no users in the playerLobby
        //and the username can be any username
        //if there are already people in playerlobby
        else
        {
            //username already exists
            if(playerLobby.getUsernames().stream().anyMatch(p1 -> p1.equals(username)))
            {
                result = UsernameResult.TAKEN;
            }
            //username does not exist in lobby and is a valid username
            else
            {
                //if there are people in the lobby but chose an acceptable username
                setUsername(username);
                playerLobby.newPlayer(this);
                result = UsernameResult.AVAILABLE;
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return username.equals(player.username);
    }

    public synchronized String getUsername(){
        return username;
    }

    public enum UsernameResult {TAKEN, AVAILABLE, INVALID}

}
