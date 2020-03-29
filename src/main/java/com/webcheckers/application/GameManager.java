package com.webcheckers.application;

import com.webcheckers.model.BoardView;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;

public class GameManager{

    private static PlayerLobby playerLobby;
    public GameManager(PlayerLobby lobby){
      playerLobby = lobby;
    }

    /**
     * Starts games, where p1 is the person starting the challenge and p2 is the
     * person accepting
     *
     * @param challenger player starting (challenger)
     * @param victim player accepting (victim)
     */
    public static void startGame(String challenger, String victim)
    {
      PlayerLobby.removeChallenger(challenger);
      Player player1 = playerLobby.getPlayers().get(challenger);
      Player player2 = playerLobby.getPlayers().get(victim);
      player1.hasEnteredGame();
      player2.hasEnteredGame();
      playerLobby.getGamesChallenge().put(challenger, victim);
      playerLobby.getGamesVictims().put(victim, challenger);
      playerLobby.getInGame().add(challenger);
      playerLobby.getInGame().add(victim);
      playerLobby.getGames().put(challenger, new CheckerGame(player1, player2, new BoardView(true)));
    }// An enum of players, player1 is the challenger and player2 is the victim.

    public static PLAYERS getNumber(String username)
    {
      if (playerLobby.getGamesChallenge().get(username) != null)
      {
        return PLAYERS.PLAYER1;
      } else
      {
        return PLAYERS.PLAYER2;
      }
    }

    public enum PLAYERS
    {PLAYER1, PLAYER2}

}
