package com.webcheckers.model;

import com.webcheckers.application.PlayerLobby;
/**
 * placeholder for actual code and information
 *
 * swen fwiends
 */

public class Player {
    public String username;
    private UsernameResult lastResult = null;

    public Player(String username){
        this.username = username;
    }

    public synchronized String getUsername(){
        return username;
    }

    public synchronized  UsernameResult getUsernameResult(){
        return lastResult;
    }
    public enum UsernameResult {TAKEN, AVAILABLE, INVALID}
}
