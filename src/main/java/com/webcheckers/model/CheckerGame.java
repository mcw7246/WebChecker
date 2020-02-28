package com.webcheckers.model;

import java.util.LinkedList;
import java.util.List;

/**
 * The model for the WebCheckers Game
 *
 * @author Zehra Amena Baig
 */
public class CheckerGame {

    /**
     * The observers of this model
     */
    /* number of rows */
    public final static int ROWS = 8;
    /* number of columns */
    public final static int COLUMNS = 8;

    public enum Square {
        COLOR, VACANCY;

    }

    /**
     * Used to indicate a move that has been made on the board,
     * and to keep track of whose turn it is
     */
    public enum Move {
        RED_PLAYER, WHITE_PLAYER, NONE;

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





}
