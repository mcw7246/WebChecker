package com.webcheckers.model;

public class Space
{
    private final boolean isblackSpace;
    private final int index;
    private Piece piece;

    public Space(int index, boolean isblackSpace, Piece piece)
    {
        this.index = index;
        this.isblackSpace = isblackSpace;
        this.piece = piece;
    }

    public Space(int index, boolean isblackSpace)
    {
        this.index = index;
        this.isblackSpace = isblackSpace;
    }

    /**
     * Returns index of column
     * @return column index of the space
     */
    public int getIndex()
    {
        return index;
    }

    /**
     * Returns whether the space is a valid space to place a piece on.
     * True if the space is black b/c can only land on black spaces.
     * @return whether the space is black or not
     */
    public boolean isValidSpace()
    {
        return isblackSpace;
    }

    /**
     * Returns the piece in the current space
     * @return the piece
     */
    public Piece getPiece()
    {
        return piece;
    }

    /**
     * Mutator method for piece attribute
     * @param piece the piece to assign to the piece attribute
     */
    public void setPiece(Piece piece)
    {
        this.piece = piece;
    }


}