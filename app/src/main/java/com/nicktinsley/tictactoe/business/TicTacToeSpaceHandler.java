package com.nicktinsley.tictactoe.business;

import android.graphics.drawable.Drawable;
import android.widget.ImageButton;

import com.nicktinsley.tictactoe.model.TicTacToeSpace;

/**
 * Created by ntinsley on 11/16/2016.
 */

public class TicTacToeSpaceHandler {
    TicTacToeSpace space;
    ImageButton btn;

    public TicTacToeSpaceHandler(TicTacToeSpace space, ImageButton btn) {
        this.space = space;
        this.btn = btn;
    }

    public void setCross(Drawable cross) {
        space.setMarked(TicTacToeSpace.MarkedState.CROSS);
        btn.setImageDrawable(cross);
    }
    public void setCircle(Drawable circle) {
        space.setMarked(TicTacToeSpace.MarkedState.CIRCLE);
        btn.setImageDrawable(circle);
    }

    public void resetState() {
        space.setMarked(TicTacToeSpace.MarkedState.UNMARKED);
        btn.setImageDrawable(null);
    }

    public String getState() {
        return space.getMarked().toString();
    }

    public boolean isUnmarked() {
        return space.getMarked() == TicTacToeSpace.MarkedState.UNMARKED;
    }
}
