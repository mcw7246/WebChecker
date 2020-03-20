package com.webcheckers.application;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.BoardView;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    CuT = new PlayerLobby();
    //creates the two players
    //friendly objects
    player1 =  new Player(CuT);
    player1.setUsername("a");
    player2 = new Player(CuT);
    player2.setUsername("b");


    CuT.newPlayer(player1);
    CuT.newPlayer(player2);
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
    assertEquals(player1.getUsername(), CuT.getChallenges().get(player2.getUsername()));

  }
  /*
   * tests getChallenges()
   */
  @Test
  public void testGet_running_challenges()
  {
    CuT.challenge(player1.getUsername(), player2.getUsername());
    assertNotNull(CuT.getChallenges());
    assertSame(CuT.getChallenges().size(), 1);
  }

  /*
   * tests tests challenging(String challenger, String victim)
   */
  @Test
  public void  testCurrent_running_challenges()
  {
    CuT.challenge(player2.getUsername(), player1.getUsername());

    assertEquals(player1.getUsername(), CuT.getChallenges().get(player2.getUsername()));

    assertTrue(CuT.challenging(player1.getUsername(), player2.getUsername()));
  }

  /**
   * not quite sure what this method is for
   */
  /*
   * tests getNumber(String username)
   */

  @Test
  public void testGet_player_number()
  {

  }

  /*
  tests method getInGame()
   */
 @Test
  public void testGet_in_game()
 {
   /**
    *finish this method when start game is done
    */
   CuT.challenge(player2.getUsername(), player1.getUsername());
   CuT.startGame(player1.getUsername(), player2.getUsername());
   assertEquals(2, CuT.getInGame().size());
 }

 /*
   Tests removeChallenger(String victim)
  */
  @Test
  public void testRemove_challenger()
  {
    CuT.challenge(player1.getUsername(), player2.getUsername());
    assertEquals(1, CuT.getChallenges().size());
    CuT.removeChallenger(player1.getUsername());
    assertEquals(0, CuT.getChallenges().size());
  }

  @Test
  public void testStart_game(){
    CuT.challenge(player2.getUsername(), player1.getUsername());
    CuT.startGame(player1.getUsername(), player2.getUsername());
    assertEquals(2, CuT.getInGame().size());
  }

  /**
   * do after the startGame test is done
   */

  @Test
  public void testGet_opponent_by_challenger()
  {
    CuT.challenge(player1.getUsername(), player2.getUsername());
    CuT.startGame(player2.getUsername(), player1.getUsername());
    assertEquals(player1, CuT.getOpponent(player2.getUsername()));
  }

  @Test
  public void testGet_opponent_by_opponent()
  {
    CuT.challenge(player1.getUsername(), player2.getUsername());
    CuT.startGame(player2.getUsername(), player1.getUsername());
    assertEquals(player2, CuT.getOpponent(player1.getUsername()));
  }

  @Test
  public void testGet_usernamens()
  {
    List<String> usernames = new ArrayList<>();
    usernames.add(player1.getUsername());
    usernames.add(player2.getUsername());
    assertEquals(usernames, CuT.getUsernames());
  }

  @Test
  public void testGet_players()
  {
    Map<String, Player> players = new HashMap<>();
    players.put(player1.getUsername(), player1);
    players.put(player2.getUsername(), player2);
    assertEquals(players, CuT.getPlayers());
  }

}
