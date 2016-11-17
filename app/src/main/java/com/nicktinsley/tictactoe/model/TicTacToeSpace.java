package com.nicktinsley.tictactoe.model;

/**
 * Created by ntinsley on 11/16/2016.
 */

public class TicTacToeSpace {
    MarkedState marked;

    public TicTacToeSpace(MarkedState marked) {
        this.marked = marked;
    }

    public MarkedState getMarked() {
        return marked;
    }

    public void setMarked(MarkedState marked) {
        this.marked = marked;
    }

    public enum MarkedState {
        UNMARKED(0),
        CROSS(1),
        CIRCLE(2);

        int code;

        MarkedState(int code) {
            this.code = code;
        }
    }
}
