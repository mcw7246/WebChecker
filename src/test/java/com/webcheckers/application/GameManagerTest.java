package com.webcheckers.application;

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
 */
@Tag("Application-tier")
public class GameManagerTest
{
  private final String P1_NAME = "player1";
  private final String P2_NAME = "player2";

  private GameManager CuT;

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
    p1 = mock(Player.class);
    p2 = mock(Player.class);
    when(p1.getUsername()).thenReturn(P1_NAME);
    when(p2.getUsername()).thenReturn(P2_NAME);
    when(p1.getPlayerNum()).thenReturn(1);
    when(p2.getPlayerNum()).thenReturn(2);
    Map<String, Player> players = new HashMap<>();
    players.put(P1_NAME, p1);
    players.put(P2_NAME, p2);
    when(lobby.getPlayers()).thenReturn(players);

    CuT = new GameManager(lobby);

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

}
