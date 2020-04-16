package com.webcheckers.application;

import com.webcheckers.model.Board;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * A test for the gameManager object
 *
 * @author Austin Miller 'akm8654'
 * @author Mikayla Wishart 'mcw7246'
 */
@Tag("Application-tier")
public class GameManagerTest
{
  private final String P1_NAME = "player1";
  private final String P2_NAME = "player2";

  private GameManager CuT;

  /*
  Friendly Objects
   */
  ReplayManager rManager;

  /*
  Mocked Objects
   */
  private PlayerLobby lobby;
  private Player p1;
  private Player p2;

  @BeforeEach
  public void setup()
  {
    lobby = mock(PlayerLobby.class);
    p1 = new Player(lobby);
    p2 = new Player(lobby);
    p1.setUsername("player1");
    p2.setUsername("player2");
    //p1 = mock(Player.class);
    //p2 = mock(Player.class);
    //when(p1.getUsername()).thenReturn(P1_NAME);
    //when(p2.getUsername()).thenReturn(P2_NAME);
    //when(p1.getPlayerNum()).thenReturn(1);
    //when(p2.getPlayerNum()).thenReturn(2);
    Map<String, Player> players = new HashMap<>();
    players.put(P1_NAME, p1);
    players.put(P2_NAME, p2);
    when(lobby.getPlayers()).thenReturn(players);
    rManager = new ReplayManager();
    CuT = new GameManager(lobby, rManager);

  }

  @Test
  public void test_Start_Game()
  {
    CuT.startGame(P1_NAME, P2_NAME);
    Set<String> inGame = CuT.getInGame();
    assertTrue(inGame.contains(P1_NAME));
    assertTrue(inGame.contains(P2_NAME));
    assertEquals(CuT.getGameID(P1_NAME), CuT.getGameID(P2_NAME));

    HashMap<String, String> pair = new HashMap<>();
    pair.put(P1_NAME, P2_NAME);
    assertEquals(pair, CuT.getPair(CuT.getGameID(P1_NAME)));
  }

  @Test
  public void test_Get_Opponent()
  {
    CuT.startGame(P1_NAME, P2_NAME);
    //Test get p1 opponent
    Player p1Opp = CuT.getOpponent(P1_NAME);
    assertEquals(p2, p1Opp);
    Player p2Opp = CuT.getOpponent(P2_NAME);
    assertEquals(p1, p2Opp);
  }

  @Test
  public void testGet_local_game(){
    CuT.startGame(P1_NAME, P2_NAME);
    int gameID = CuT.getGameID(P1_NAME);
    CheckerGame clientGame = CuT.makeClientSideGame(gameID, p1.getUsername());
    CuT.getLocalGame(p1.getUsername());
    assertEquals(clientGame, CuT.getLocalGame(p1.getUsername()));

  }
  @Test
  public void testMake_client_side_game(){
    CuT.startGame(p1.getUsername(), p2.getUsername());
    int gameID = CuT.getGameID(p1.getUsername());

    CheckerGame p1Game = CuT.makeClientSideGame(gameID, p1.getUsername());
    CheckerGame p2Game = CuT.makeClientSideGame(gameID, p2.getUsername());

    CheckerGame p1ClientSideGame = CuT.getLocalGame(p1.getUsername());
    CheckerGame p2ClientSideGame = CuT.getLocalGame(p2.getUsername());
    assertEquals(p1ClientSideGame, p1Game);
    assertEquals(p2ClientSideGame, p2Game);
  }

  @Test
  public void testUpdate_game(){
    CuT.startGame(p1.getUsername(), p2.getUsername());

    int gameID = CuT.getGameID(p1.getUsername());

    Board newBoard = new Board();
    CheckerGame updatedGame = new CheckerGame(p1, p2, newBoard);
    CuT.updateGame(gameID, updatedGame);

    assertEquals(updatedGame, CuT.getGame(gameID));
  }


}
