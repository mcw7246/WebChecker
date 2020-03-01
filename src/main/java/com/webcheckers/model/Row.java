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
    private ArrayList<Space> spaces;
    /**
     * The index of the row
     */
    private final int index;

    /**
     * Constructor for row class
     * @param index The index of the row
     * @param first used in initializeBoard method for piece arrangement
     */
    public Row(int index, boolean first)
    {
        this.index = index;
        this.spaces = new ArrayList<Space>();
        initializeBoard(first);
    }

    /**
     * Initializes the board with alternating b&w spaces
     * And arranges the pieces for the start of the game
     */
    public void initializeBoard(boolean first)
    {
        boolean isBlackSpace;

        if(index % 2 == 1)
        {
            isBlackSpace = true;
        }
        else
        {
            isBlackSpace = false;
        }

        //Constructs board with alternating b&w tiles
        for(int i = 0; i < DIMENSIONS; i++)
        {
            spaces.add(new Space(i, isBlackSpace));
            isBlackSpace = !isBlackSpace;
        }

    }

    /**
     * Returns the index of the row
     * @return the row's index
     */
    public int getIndex()
    {
        return this.index;
    }

    @Override
    public Iterator<Space> iterator()
    {
        return spaces.iterator();
    }

    /**
     * Will place pieces on a row in an alternating pattern
     * Meant to be used only as a helper function for init when starting a game
     * @param piece The piece to add to the row
     */
    public void addPieces(Piece piece){
        for(Space space: spaces){
            if(space.isValidSpace()){
                space.setPiece(piece);
            }
        }
    }
}
