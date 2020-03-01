package com.webcheckers.application;
import com.webcheckers.model.GameModel;

/**
 * The Move Application tier
 *
 * @author Zehra Amena Baig
 */
public class Move
{
    GameModel gameModel = new GameModel();

    /**
     * Will this move be accepted as valid?
     * This method is added so that a bad move is caught before it is made.
     *
     * @return true if the chosen square is red and not occupied
     */
    public boolean isDroppable(GameModel.Square sq)
    {
        if((sq.COLOR.equals(GameModel.activeColor.WHITE)) || !(sq.PIECE.equals(GameModel.Piece.NONE)))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
