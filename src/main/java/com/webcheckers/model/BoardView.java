package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The Board itself
 *
 * @author Zehra Amena Baig (zab1166)
 */

public class BoardView implements Iterable<Row>
{
    /**
     * The board's size
     */
    public static final int DIMENSIONS = 8;
    /**
     * The board itself
     */
    private List<Row> board;

    /**
     * enum for the result of a move
     */
    public enum Move
    {
        SINGLE, MULTI, MOVE, INVALID, WIN
    }

    /**
     * BoardView Constructor
     * Initializes the board spaces
     *
     * @param isFirst holds whether player is 1st (red) or not
     */
    public BoardView(boolean isFirst)
    {
        board = new ArrayList<Row>();
        for(int i = 0; i < DIMENSIONS; i++)
        {
            board.add(new Row(i, isFirst));
        }
    }

    /**
     * Determines if move is valid or not
     * @param startRow the row index of the current space
     * @param landRow the row index of the space the user wants to land on
     * @param startCol the column index of the current space
     * @param landCol the column index of the space the user wants to land on
     * @return whether the move is valid or not
     */
    public boolean isMoveValid(int startRow, int  landRow, int startCol, int landCol)
    {
        return true;
    }

    @Override
    public Iterator<Row> iterator()
    {
        return board.iterator();
    }

}
