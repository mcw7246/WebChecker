package com.webcheckers.model;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player.UsernameResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The unit test suite for the {@link Piece} component.
 *
 * @author Sean Bergen 'sdb2139'
 */
public class PieceTest
{
    /**
     * Snapshot of the Piece Class:
     *
     * + Color       enum
     * + Type        enum
     * - color       Color
     * - type        Type
     *
     * + getColor()  Color
     * + getType()   Type
     */

    /**
     * Piece is the component under test (CuT)
     *
     *
     */
    private Piece CuT;

    @BeforeEach
    public void setup()
    {

    }

    /**
     * Tests that the constructor works with arguments
     */
    @Test
    public void ctor_withArg()
    {
        CuT = new Piece(Piece.Color.RED, Piece.Type.SINGLE);
    }

    /**
     * Tests that the getColor method works
     */
    @Test
    public void testGetColor()
    {
        Piece CuT1 = new Piece(Piece.Color.RED, Piece.Type.SINGLE);
        Piece CuT2 = new Piece(Piece.Color.WHITE, Piece.Type.SINGLE);

        assertNotEquals(CuT1.getColor(), CuT2.getColor());
        assertEquals(Piece.Color.RED, CuT1.getColor());
        assertNotEquals(Piece.Color.WHITE, CuT1.getColor());
        assertEquals(Piece.Color.WHITE, CuT2.getColor());
        assertNotEquals(Piece.Color.RED, CuT2.getColor());
    }

    /**
     * Tests that the getType method works
     */
    @Test
    public void testGetType()
    {
        Piece CuT1 = new Piece(Piece.Color.RED, Piece.Type.SINGLE);
        Piece CuT2 = new Piece(Piece.Color.RED, Piece.Type.KING);

        assertNotEquals(CuT1.getType(),CuT2.getType());
        assertEquals(Piece.Type.SINGLE, CuT1.getType());
        assertNotEquals(Piece.Type.KING, CuT1.getType());
        assertEquals(Piece.Type.KING, CuT2.getType());
        assertNotEquals(Piece.Type.SINGLE, CuT2.getType());
    }

    /**
     * Tests that the setType method works
     */
    @Test
    public void testSetType()
    {
        Piece CuT1 = new Piece(Piece.Color.RED, Piece.Type.SINGLE);
        Piece CuT2 = CuT1;
        assertEquals(CuT1, CuT2);
        CuT2.setType(Piece.Type.KING);
    }
}
