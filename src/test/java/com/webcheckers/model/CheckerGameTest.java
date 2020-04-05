package com.webcheckers.model;

import com.webcheckers.application.PlayerLobby;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@Tag("UI-tier")
public class CheckerGameTest {

    //The component under test (CuT)
    private CheckerGame CuT;

    //Another CheckerGame
    private static CheckerGame copy;

    //Friendly objects
    private static PlayerLobby lobby;
    private static Move testMove;
    private static Space space;
    private Player player1, player2;
    private Board board;

    @BeforeAll
    public static void init()
    {
        lobby = new PlayerLobby();
        testMove = new Move(new Position(1, 0), new Position(2, 1));
        space = new Space(6, 7, true, new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));
        copy = new CheckerGame(new Player(lobby), new Player(lobby), new Board());
    }

    @BeforeEach
    public void setup()
    {
        player1 = new Player(lobby);
        player1.setUsername("player1");
        player2 = new Player(lobby);
        player2.setUsername("player2");
        board = new Board();
        CuT = new CheckerGame(player1, player2, board);
    }

    @Test
    public void ctorTest()
    {
        assertNotNull(CuT);
        CuT = new CheckerGame(copy);
        assertNotNull(CuT);
    }

    @Test
    public void playerOneTurn()
    {
        assertEquals("player1", CuT.getTurn());
        assertEquals(Piece.Color.RED, CuT.getColor());
    }

    @Test
    public void playerTwoTurn()
    {
        CuT.updateTurn();
        assertEquals("player2", CuT.getTurn());
        assertEquals(Piece.Color.WHITE, CuT.getColor());
    }

    @Test
    public void gettersTest()
    {
        assertEquals(player1, CuT.getRedPlayer());
        assertEquals(player2, CuT.getWhitePlayer());
        assertEquals(board, CuT.getBoard());
    }

    @Test
    public void movedTest()
    {
        CuT.setMoved(true);
        assertEquals(true, CuT.hasMoved());
        CuT.setMoved(false);
        assertEquals(false, CuT.hasMoved());
    }

    @Test
    public void jumpedPieceTest()
    {
        CuT.addJumpedPieces(space);
        assertEquals(space, CuT.getJumpedPiece());
        assertNull(CuT.getJumpedPiece());
    }

    @Test
    public void makeMoveTest()
    {
        CuT.makeMove(testMove);
    }

}