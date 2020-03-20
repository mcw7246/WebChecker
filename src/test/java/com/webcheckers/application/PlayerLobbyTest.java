package com.webcheckers.application;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Player;
import com.webcheckers.ui.GetHomeRoute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("Application-tier")
public class PlayerLobbyTest
{
  /*
   *the component under test
   */
  private PlayerLobby CuT;

  /*
   *Friendly objects
   */
  private Player player1;
  private Player player2;


  /*
   *attributes holding moc objects
   */


  @BeforeEach
  public void setup()
  {
    //creates the two players
    //friendly objects
    player1 =  new Player(CuT);
    player1.setUsername("a");
    player2 = new Player(CuT);
    player2.setUsername("b");

    CuT = new PlayerLobby();
  }

  @Test
  public void testNew_player()
  {
     int previousSize = CuT.getPlayers().size();
     Player newPlayer = new Player(CuT);
     newPlayer.setUsername("c");
     CuT.newPlayer(newPlayer);

     assertTrue(previousSize + 1 == CuT.getPlayers().size());
  }

  @Test
  public void testNBeing_challenged_rejected()
  {
    Player player3 = new Player(CuT);
    player3.setUsername("c");
    //creates the first game between player1 and player2
    CuT.challenge(player1.getUsername(), player2.getUsername());

    assertFalse(CuT.challenge(player1.getUsername(), player3.getUsername()));
  }

  @Test
  public void testBeing_challenged_approved()
  {
    //creates the challenge between the two individuals
    CuT.challenge(player2.getUsername(), player1.getUsername());
    assertEquals(CuT.getChallenges().get(player1.getUsername()), player2.getUsername());

  }

  @Test
  public void testGet_running_challenges()
  {
    CuT.challenge(player1.getUsername(), player2.getUsername());
    assertNotNull(CuT.getChallenges());
    assertSame(CuT.getChallenges().size(), 1);
  }

  @Test
  public void testCurrent_running_challenges()
  {
    CuT.challenge(player2.getUsername(), player1.getUsername());

    assertEquals(CuT.getChallenges().get(player2.getUsername()), player1.getUsername());

    assertTrue(CuT.challenging(player2.getUsername(), player1.getUsername()));
  }

  /**
   * not quite sure what this method is for
   */
  /*
  @Test
  public void testGet_player_number()
  {

  }*/

 @Test
  public void testGet_in_game()
 {
   assertEquals(CuT.getInGame().size(), 0);
 }
/*
 @Test
  public void testStart_game()
 {
   CuT.challenge(player2.getUsername(), player1.getUsername());
   CuT.startGame(player1.getUsername(), player2.getUsername());

   assertNotNull(CuT.getGame(player1.getUsername()));
 }*/

  /**
   * do after the startGame test is done
   */
   @Test
  public void testGet_opponent_by_challenger()
  {
   CuT.challenge(player2.getUsername(), player1.getUsername());
    CuT.startGame(player1.getUsername(), player2.getUsername());
   assertEquals(CuT.getOpponent(player1.getUsername()), player2);
  }
}
