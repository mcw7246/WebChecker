package com.webcheckers.model;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player.UsernameResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The unit test suite for the {@link CheckerGame} component.
 *
 * @author Sean Bergen 'sdb2139'
 */
public class CheckerGameTest
{

    /**
     * CheckerGame can be summarized as:
     *
     * Attributes
     *   -  PLAYER_ONE            Player
     *   -  PLAYER_TWO            Player
     *   -  board                 BoardView
     *
     * Methods
     *   +  getRedPlayer          Player
     *   +  getWhitePlayer        Player
     *   +  getBoardView          BoardView
     *   +  getFlippedBoardView   BoardView
     */

    private static final boolean TRUE = true;
    private static final boolean FALSE = false;


    /**
     * The component under test.
     * <p>
     * Stateless component that actually test the functionality of the
     * {@link CheckerGame}
     */
    private CheckerGame CuT;

    // Friendly objects
    private Player player1;
    private Player player2;
    private BoardView boardT;
    private BoardView boardF;
    private PlayerLobby lobby;

    @BeforeEach
    public void setup()
    {
        lobby = new PlayerLobby();
        player1 = new Player(lobby);
        player2 = new Player(lobby);
        boardT = new BoardView();
        boardF = boardT;
        boardF.flip();

        player1.setUsername("a");
        player2.setUsername("b");

        lobby.newPlayer(player1);
        lobby.newPlayer(player2);

    }

    /**
     * Tests that the main constrcutor works without failure.
     */
    @Test
    public void ctor_withArg()
    {
        CuT = new CheckerGame(player1, player2, boardT);
    }


    /**
     * Tests that the getRedPlayer method returns the red player and not the white player
     */
    @Test
    public void testGetRedPlayer()
    {
        CuT = new CheckerGame(player1,player2,boardT);
        assertEquals(CuT.getRedPlayer(),player1);
        assertNotEquals(CuT.getRedPlayer(),player2);
        assertNotEquals(CuT.getWhitePlayer(),player1);
    }

    /**
     * Tests that the getWhitePlayer method returns the white player and not the red player
     */
    @Test
    public void testGetWhitePlayer()
    {
        CuT = new CheckerGame(player1,player2,boardT);
        assertEquals(CuT.getWhitePlayer(),player2);
        assertNotEquals(CuT.getRedPlayer(),player2);
        assertNotEquals(CuT.getWhitePlayer(),player1);
    }

    /**
     * Tests getBoardView
     */
    @Test
    public void testGetBoardView()
    {
        CuT = new CheckerGame(player1,player2,boardT);
        assertEquals(CuT.getBoardView(),boardT);
        assertNotEquals(CuT.getBoardView(),boardF);
        assertNotEquals(CuT.getFlippedBoardView(),boardT);
    }

    /**
     * Tests getFlippedBoardView
     */
    @Test
    public void testGetFlippedBoardView()
    {
        CuT = new CheckerGame(player1,player2,boardT);
        assertNotEquals(CuT.getFlippedBoardView(),boardT);
    }
}
