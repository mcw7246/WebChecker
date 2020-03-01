package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

public class Row implements Iterable<Space>
{
    /**
     * The size of the row
     */
    private final static int DIMENSIONS = 8;
    /**
     * The ArrayList of spaces in the row
     */
    private ArrayList<Space> squares;
    /**
     * The index of the row
     */
    private final int rowIndex;

    /**
     * Constructor for row class
     * @param rowIndex The index of the row
     * @param first used in initializeBoard method for piece arrangement
     */
    public Row(int rowIndex, boolean first)
    {
        this.rowIndex = rowIndex;
        this.squares = new ArrayList<Space>();
        initializeBoard(first);
    }

    /**
     * Initializes the board with alternating b&w spaces
     * And arranges the pieces for the start of the game
     */
    private void initializeBoard(boolean first)
    {
        boolean isBlackSpace;
        Piece red = new Piece(Piece.Color.RED, Piece.Type.SINGLE);
        Piece white = new Piece(Piece.Color.WHITE, Piece.Type.SINGLE);

        if(rowIndex % 2 == 1)
        {
            isBlackSpace = true;
        }
        else
        {
            isBlackSpace = false;
        }

        for(int i = 0; i < DIMENSIONS; i++)
        {
            squares.add(new Space(i, isBlackSpace));
            isBlackSpace = !isBlackSpace;
        }

        placePieces(first, red, white);
    }

    /**
     * Helper method to place pieces on board
     * @param first whether player is first or not (determines arrangement of piece colors)
     * @param red red piece
     * @param white white piece
     */
    private void placePieces(boolean first, Piece red, Piece white)
    {
        if(first)
        {
            if(rowIndex == DIMENSIONS - 1 || rowIndex == DIMENSIONS - 2)
            {
                fillRow(red);
            }
            else if(rowIndex == 0|| rowIndex == 1)
            {
                fillRow(white);
            }
        }
        else
        {
            if(rowIndex == DIMENSIONS - 1 || rowIndex == DIMENSIONS - 2)
            {
                fillRow(white);
            }
            else if(rowIndex == 0|| rowIndex == 1)
            {
                fillRow(red);
            }
        }
    }

    /**
     * Places pieces in row in alternating fashion
     * @param piece The piece to place on the squares
     */
    public void fillRow(Piece piece){
        for(Space s: squares)
        {
            if(s.isValidSpace())
            {
                s.setPiece(piece);
            }
        }
    }

    /**
     * Returns the index of the row
     * @return the row's index
     */
    public int getIndex()
    {
        return this.rowIndex;
    }


    @Override
    public Iterator<Space> iterator()
    {
        return squares.iterator();
    }
}
