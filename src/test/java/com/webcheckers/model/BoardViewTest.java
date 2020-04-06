package com.webcheckers.model;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player.UsernameResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit test suite for the {@link BoardView} component.
 *
 * @author Sean Bergen 'sdb2139'
 */
public class BoardViewTest
{
    /**
     * Snapshot of the BoardView Class:
     *
     * - board                             List<Row>
     * - flipped                           boolean
     *
     * + makeCopyOfBoard(List<Row> board)  List<Row>
     * + flip                              void
     * + getBoard                          List<Row>
     */

    private Board board;

    @BeforeEach
    public void setup()
    {
        board = mock(Board.class);
    }

    @Test
    public void ctor_withArgs()
    {
        BoardView CuT = new BoardView(board);
    }

    @Test
    public void testMakeCopyOfBoard()
    {

    }

    @Test
    public void testFlip()
    {

    }

    @Test
    public void testGetBoard()
    {

    }

}
