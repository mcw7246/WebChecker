package com.webcheckers.model;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player.UsernameResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.webcheckers.model.Board.DIMENSIONS;
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
        board = new Board();
    }

    @Test
    public void ctor_withArgs()
    {
        // BoardView CuT = new BoardView(board);
    }

    @Test
    public void testCopyBoard()
    {
        BoardView CuT1 = new BoardView(board);

        List<Row> a = CuT1.getBoard();
        List<Row> b = board.getBoard();

        for (int i = 0; i < DIMENSIONS; i++)
        {
            //System.out.println(a.get(i).getIndex());
            //System.out.println(b.get(i).getIndex());
            if(a.get(i).getIndex() != b.get(i).getIndex())
            {
                assertEquals(true, false);
            }
        }
        assertEquals(true, true);
    }

    @Test
    public void testGetBoard()
    {
        BoardView CuT1 = new BoardView(board);

        List<Row> a = CuT1.getBoard();
        List<Row> b = board.getBoard();

        for (int i = 0; i < DIMENSIONS; i++)
        {
            if(a.get(i).getIndex() != b.get(i).getIndex())
            {
                assertEquals(true, false);
            }
        }
        assertEquals(true, true);
    }


    @Test
    public void testFlip()
    {
        BoardView CuT1 = new BoardView(board);
        BoardView CuT2 = new BoardView(board);
        CuT2.flip();
        assertNotEquals(CuT1.getBoard(), CuT2.getBoard());
    }
}
