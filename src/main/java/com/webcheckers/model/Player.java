package com.webcheckers.model;

import com.webcheckers.application.PlayerLobby;

import java.util.regex.Pattern;

/**
 * placeholder for actual code and information
 *
 * @author Austin Miller 'akm8654' Mikayla Wishart 'mcw7246'
 */

public class Player
{
  public static UsernameResult result = null;
  private String username;
  private PlayerLobby playerLobby;
   private boolean inGame = false;

  /**
   * constructor for Player
   * @param playerLobby the lobby that the user is going into
   */
  public Player(PlayerLobby playerLobby)
  {
      this.playerLobby = playerLobby;
  }

    /**
     * Toggles that the player is in game.
     */
    public void hasEnteredGame() {
        inGame = true;
    }

    /**
     * Gets the opponent of this player.
     */
    public String getOpponent() {
        return playerLobby.getChallenger(username);
    }

    /**
     * Returns whether the player is actively in a game or not.
     *
     * @return true or false for in game.
     */
  public boolean isInGame()
  {
    return inGame;
  }

  /**
   *
   * @param name the username that the user submits
   * @return the username
  */
  public String setUsername(String name)
  {
    username = name;
    return username;
  }
  //constructor: saves the username that the player wants

  /**
   * checks if username is valid
   * @param username the username that the user inputs
   * @return the enum for UsernameResult
  */
  public synchronized UsernameResult isValidUsername(String username)
  {
    /**
     * make sure that the username contains letters and numbers and spaces only
     */

    boolean userContains = Pattern.matches("[a-zA-Z0-9]+", username);
    if(!userContains)
    {
      result = UsernameResult.INVALID;
      return result;
    }
    /**
     * if the arrayList.size() == 0 then there are no users in the playerLobby
     * and the username can be any username
     * if there are already people in playerlobby
     */
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

  /**
   *
   * @param o object that you are compete
   * @return
   */
  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
     return true;
    }
    if (o == null || getClass() != o.getClass())
    {
      return false;
    }
    Player player = (Player) o;
    return username.equals(player.username);
  }

  /**
   *
   * @return
   */
  public synchronized String getUsername()
  {
    return username;
  }
  public enum UsernameResult {TAKEN, AVAILABLE, INVALID}
}
