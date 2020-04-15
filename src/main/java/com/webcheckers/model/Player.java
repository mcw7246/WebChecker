package com.webcheckers.model;

import com.webcheckers.application.PlayerLobby;

import java.util.regex.Pattern;

//import static jdk.vm.ci.meta.JavaKind.Char;

/**
 * placeholder for actual code and information
 *
 * @author Austin Miller 'akm8654' Mikayla Wishart 'mcw7246'
 */

public class Player
{
  public static UsernameResult result = null;

  public enum ViewMode
  {
    PLAY
  }

  private String username;
  private PlayerLobby playerLobby;
  private boolean inGame = false;
  private int playerNum = 1;

  /**
   * constructor for Player
   *
   * @param playerLobby the lobby that the user is going into
   */
  public Player(PlayerLobby playerLobby)
  {
    this.playerLobby = playerLobby;
  }

  /**
   * Updates the player into the second player.
   */
  public void makePlayer2()
  {
    this.playerNum = 2;
  }

  /**
   * A getter for the player number.
   *
   * @return either 1 or 2 depending on if they challenged or were the victim.
   */
  public int getPlayerNum()
  {
    return playerNum;
  }

  /**
   * Toggles that the player is in game.
   */
  public void hasEnteredGame()
  {
    inGame = true;
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
   * @param name the username that the user submits
   */
  public void setUsername(String name)
  {
    username = name;
  }
  //constructor: saves the username that the player wants

  /**
   * checks if username is valid
   *
   * @param username the username that the user inputs
   * @return the enum for UsernameResult
   */
  public synchronized UsernameResult isValidUsername(String username)
  {
    /*
     * make sure that the username contains letters and numbers and spaces only
     */

    boolean userContains = Pattern.compile("[a-z]+").matcher(username).find();
    boolean containsSpace = Pattern.compile("\\s\\s").matcher(username).find();
    String[] usernameList = username.split("");
    boolean numContains = Pattern.compile("[0-9]*").matcher(username).find();
    System.out.println(usernameList[0]);
    boolean firstNum = usernameList[0].matches("[0-9]");
    boolean specialChar = Pattern.compile("[^a-zA-Z0-9\\s]").matcher(username).find();
    System.out.println(specialChar);
    if (!userContains || username.length() < 6 || username.length() > 25 || firstNum || specialChar)
    {
      result = UsernameResult.INVALID;
      return result;
    }

    /*
     * if the arrayList.size() == 0 then there are no users in the playerLobby
     * and the username can be any username
     * if there are already people in playerlobby
     */
    else
    {
      if(containsSpace)
      {
        String trimmedUsername = username.trim();
        if(playerLobby.getUsernames().contains(trimmedUsername)){
          result = UsernameResult.TAKEN;
        }
        else{
          setUsername(trimmedUsername);
          playerLobby.newPlayer(this);
          result = UsernameResult.AVAILABLE;
        }
      }

      //username already exists
      else if (playerLobby.getUsernames().stream().anyMatch(p1 -> p1.equals(username)))
      {
        result = UsernameResult.TAKEN;
      } else
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
   * @param o object that you are compete
   *
   * @return whether the two objects are equal or not.
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
   * Gets the username for the specified player.
   *
   * @return the username of the player.
   */
  public synchronized String getUsername()
  {
    return username;
  }

  public enum UsernameResult
  {TAKEN, AVAILABLE, INVALID}
}
