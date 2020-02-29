package com.webcheckers.application;

import com.webcheckers.model.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerLobby
{
    public List<Player> users = new ArrayList<>();

    //creates a new Player and adds it to an array list of players
    public Player newPlayer(Player player)
    {
        users.add(player);
        return player;
    }

   public List<String> getUsernamesList()
   {
       List<String> usernamesList = new ArrayList<>();
       for(Player player : users){
           usernamesList.add(player.getUsername());
       }
       return usernamesList;
   }


}
