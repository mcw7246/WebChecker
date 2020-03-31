package com.webcheckers.application;

import com.webcheckers.model.BoardView;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import com.webcheckers.ui.GetGameRoute;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameManager{

    private static Map<String, Integer> gameIDChallenger = new HashMap<>();
    private static Map<String, Integer> gameIDVictim = new HashMap<>();
    private static int gameIDNum = 1;

    //map of the current games going on where the key is the GameID and the value is the CheckerGame associated with it
    private static Map<Integer, CheckerGame> games = new HashMap<>();
    private static Map<String,String> gamesVictim= new HashMap<>();
    private static Map<String,String> gamesChallenge = new HashMap<>();
    private static Set<String> inGame = new HashSet<>();
    private boolean p1Turn = true;
    private boolean p2Turn = false;

    private static PlayerLobby playerLobby;
    public GameManager(PlayerLobby lobby){
      playerLobby = lobby;
    }

    public static Map<String, String> getGamesChallenge(){return gamesChallenge;}

    public static Map<String, String> getGamesVictim(){return gamesVictim;}

  public static Set<String> getInGame(){return inGame;}

  public static Map<Integer, CheckerGame> getGames(){return games;}

    /**
     * Starts games, where p1 is the person starting the challenge and p2 is the
     * person accepting
     *
     * @param challenger player starting (challenger)
     * @param victim player accepting (victim)
     */
    public void startGame(String challenger, String victim)
    {
      playerLobby.removeChallenger(challenger);
      Player player1 = playerLobby.getPlayers().get(challenger);
      Player player2 = playerLobby.getPlayers().get(victim);
      player1.hasEnteredGame();
      player2.hasEnteredGame();
      getGamesChallenge().put(challenger, victim);
      getGamesVictim().put(victim, challenger);
      getInGame().add(challenger);
      getInGame().add(victim);
      gameIDNum +=1;
      gameIDChallenger.put(challenger, gameIDNum);
      gameIDVictim.put(victim, gameIDNum);
      getGames().put(gameIDNum, new CheckerGame(player1, player2, new BoardView(true)));
    }// An enum of players, player1 is the challenger and player2 is the victim.

    //returns the gameID for the given player
    public int getGameID(String player){
      if (gameIDVictim.get(player) == null)
      {
        return gameIDChallenger.get(player);
      }
      else{
        return gameIDVictim.get(player);
      }
    }

    public CheckerGame getGame(int currentGameID){
      return games.get(currentGameID);
    }

    public PLAYERS getNumber(String username)
    {
      if (getGamesChallenge().get(username) != null)
      {
        return PLAYERS.PLAYER1;
      } else
      {
        return PLAYERS.PLAYER2;
      }
    }

  public Player getOpponent(String username)
  {
    String opponent = gamesChallenge.get(username);
    if (opponent != null)
    {
      return playerLobby.getPlayers().get(opponent);
    } else
    {
      return playerLobby.getPlayers().get(gamesVictim.get(username));
    }
  }
    public enum PLAYERS
    {PLAYER1, PLAYER2}

}
