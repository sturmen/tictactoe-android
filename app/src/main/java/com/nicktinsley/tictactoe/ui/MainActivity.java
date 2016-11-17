package com.nicktinsley.tictactoe.ui;

import android.content.DialogInterface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.nicktinsley.tictactoe.R;
import com.nicktinsley.tictactoe.business.TicTacToeSpaceHandler;
import com.nicktinsley.tictactoe.model.TicTacToeSpace;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nicktinsley.tictactoe.business.WinChecker.checkForTie;
import static com.nicktinsley.tictactoe.business.WinChecker.checkForWin;

/**
 * This is the main activity class, where all the UI lives.
 */
public class MainActivity extends AppCompatActivity {
    public static final int BOARD_SIZE = 4; // This can be used to change the board game size.
    private static final String TAG = "TicTacToe";

    @BindView(R.id.grid_layout)
    GridLayout gridLayout;
    List<TicTacToeSpaceHandler> ticTacToeSpaces = new ArrayList<>();

    boolean circleTurn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initGrid(gridLayout);
        initState(gridLayout);
    }

    /**
     * This constructs the buttons and inserts them into the GridLayout.
     * @param grid
     */
    private void initGrid(GridLayout grid) {
        int totalSpaces = BOARD_SIZE * BOARD_SIZE;
        grid.setColumnCount(BOARD_SIZE);
        for (int i = 0; i < totalSpaces; i++) {
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 100;
            params.height = 100;
            ImageButton btn = new ImageButton(this);
            btn.setLayoutParams(params);
            grid.addView(btn);
            TicTacToeSpace ticTacToeSpace = new TicTacToeSpace(TicTacToeSpace.MarkedState.UNMARKED);
            final TicTacToeSpaceHandler handler = new TicTacToeSpaceHandler(ticTacToeSpace, btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buttonPressed(handler);
                }
            });
            ticTacToeSpaces.add(handler);
        }
    }

    public void buttonPressed(TicTacToeSpaceHandler handler) {
        if (handler.isUnmarked()) {
            if (circleTurn) {
                handler.setCircle(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_circle, null));
            } else {
                handler.setCross(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_cross, null));
            }
//            printState(ticTacToeSpaces);
            if (checkForWin(ticTacToeSpaces)) {
                Log.d(TAG, "We have a winner!");
                showEndDialog(circleTurn ? R.string.circle_win_message : R.string.cross_win_message);
            }

            if (checkForTie(ticTacToeSpaces)) {
                Log.d(TAG, "We have a tie!");
                showEndDialog(R.string.tie_game);
            }
            circleTurn = !circleTurn;
        } else {
            Toast.makeText(this, R.string.not_allowed, Toast.LENGTH_SHORT).show();
        }
    }

    // private debugging method
    private void printState(List<TicTacToeSpaceHandler> ticTacToeSpaces) {
        for (int i = 0; i < ticTacToeSpaces.size(); i++) {
            TicTacToeSpaceHandler handler = ticTacToeSpaces.get(i);
            Log.d(TAG, "Space: " + i + "\tState: " + handler.getState());
        }
    }

    public void showEndDialog(int msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage(msg)
                .setPositiveButton(getResources().getString(R.string.play_again), null)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                initState(gridLayout);
            }
        });
        builder.create().show();
    }

    public void initState(View view) {
        circleTurn = false;
        for (TicTacToeSpaceHandler handler : ticTacToeSpaces) {
            handler.resetState();
        }
    }
}
