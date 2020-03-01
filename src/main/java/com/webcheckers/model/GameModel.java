package com.webcheckers.model;

import java.util.LinkedList;
import java.util.List;

/**
 * The model for the WebCheckers Game
 *
 * @author Zehra Amena Baig
 */
public class GameModel {

    /* dimensions */
    public final static int DIM = 8;

    public enum activeColor{RED, WHITE}

    public enum Piece{RED, WHITE, NONE}

    /**
     * Represents each square on the board
     */
    public class Square{
        public activeColor COLOR;
        public Piece PIECE;
    }

    /**
     * Used to indicate a move that has been made on the board,
     * and to keep track of whose turn it is
     */
    public enum Move {
        RED_PLAYER, WHITE_PLAYER;

        public Move opponent() {
            return this == RED_PLAYER ?
                    WHITE_PLAYER :
                    this == WHITE_PLAYER ?
                            RED_PLAYER:
                            this;
        }
    }

    /**
     * Possible statuses of game
     */
    public enum Status {
        NOT_OVER, I_WON, I_LOST, TIE, ERROR;

        private String message = null;

        public void setMessage( String msg ) {
            this.message = msg;
        }

        @Override
        public String toString() {
            return super.toString() +
                    this.message == null ? "" : ( '(' + this.message + ')' );
        }
    }

    /**
     *
     */
    private boolean myTurn;

    /**
     * This value flips back and forth as discs are added to the board.
     */
    private Move currentPiece;

    /**
     * Current game status
     */
    private Status status;

    /**
     * The board matrix of squares
     */
    private Move[][] board;

    /**
     * Can the local user make changes to the board?
     * @return true if the server has told this player it is its time to move
     */
    public boolean isMyTurn() {
        return this.myTurn;
    }

    /**
     * The user has chosen a move.
     */
    public void didMyTurn() {
        this.myTurn = false;
    }

    public Status getStatus() {
        return this.status;
    }




}
