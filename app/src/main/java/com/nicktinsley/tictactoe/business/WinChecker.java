package com.nicktinsley.tictactoe.business;

import android.util.Log;

import com.nicktinsley.tictactoe.model.TicTacToeSpace;

import java.util.ArrayList;
import java.util.List;

import static com.nicktinsley.tictactoe.ui.MainActivity.BOARD_SIZE;

/**
 * Created by ntinsley on 11/16/2016.
 */

public class WinChecker {
    private static final String TAG = "WinChecker";

    public static boolean checkForTie(List<TicTacToeSpaceHandler> handlers) {
        for (TicTacToeSpaceHandler handler : handlers) {
            if (handler.isUnmarked()) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkForWin(List<TicTacToeSpaceHandler> handlers) {
        List<TicTacToeSpace> state = new ArrayList<>();
        for (TicTacToeSpaceHandler handler : handlers) {
            state.add(handler.space);
        }
        return checkforVertical(state) || checkForHorizontal(state) || checkForDiagonal(state) || checkCorners(state) || checkBox(state);
    }

    private static boolean checkforVertical(List<TicTacToeSpace> state) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            TicTacToeSpace[] spacesToCheck = new TicTacToeSpace[BOARD_SIZE];
            for (int j = 0; j < BOARD_SIZE; j++) {
                spacesToCheck[j] = state.get(i + (BOARD_SIZE * j));
            }
            if (allTheSameAndNotUnmarked(spacesToCheck)) {
                Log.d(TAG, "Vertical win!");
                return true;
            }
        }
        return false;
    }

    private static boolean checkForHorizontal(List<TicTacToeSpace> state) {
        for (int i = 0; i < (BOARD_SIZE * BOARD_SIZE); i += BOARD_SIZE) {
            TicTacToeSpace[] spacesToCheck = new TicTacToeSpace[BOARD_SIZE];
            for (int j = 0; j < BOARD_SIZE; j++) {
                spacesToCheck[j] = state.get(i + j);
            }
            if (allTheSameAndNotUnmarked(spacesToCheck)) {
                Log.d(TAG, "Horizontal win!");
                return true;
            }
        }
        return false;
    }

    private static boolean checkForDiagonal(List<TicTacToeSpace> state) {
        return checkForDescendingDiagonal(state) || checkForAscendingDiagonal(state);
    }

    private static boolean checkForDescendingDiagonal(List<TicTacToeSpace> state) {
        TicTacToeSpace[] spacesToCheck = new TicTacToeSpace[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            spacesToCheck[i] = state.get(i * (BOARD_SIZE + 1));
        }
        if (allTheSameAndNotUnmarked(spacesToCheck)) {
            Log.d(TAG, "Descending diagonal win!");
            return true;
        } else {
            return false;
        }
    }

    private static boolean checkForAscendingDiagonal(List<TicTacToeSpace> state) {
        TicTacToeSpace[] spacesToCheck = new TicTacToeSpace[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            spacesToCheck[i] = state.get((i + 1) * (BOARD_SIZE - 1));
        }
        if (allTheSameAndNotUnmarked(spacesToCheck)) {
            Log.d(TAG, "Ascending diagonal win!");
            return true;
        } else {
            return false;
        }
    }

    private static boolean checkCorners(List<TicTacToeSpace> state) {
        TicTacToeSpace[] spacesToCheck = new TicTacToeSpace[4];
        spacesToCheck[0] = state.get(0);
        spacesToCheck[1] = state.get(BOARD_SIZE - 1);
        spacesToCheck[2] = state.get(BOARD_SIZE * (BOARD_SIZE - 1));
        spacesToCheck[3] = state.get((BOARD_SIZE * BOARD_SIZE) - 1);
        if (allTheSameAndNotUnmarked(spacesToCheck)) {
            Log.d(TAG, "Corner win!");
            return true;
        } else {
            return false;
        }

    }
    private static boolean checkBox(List<TicTacToeSpace> state) {
        double boxLengthSize = Math.floor(BOARD_SIZE / 2.0);
        int boxSize = (int) (boxLengthSize * boxLengthSize);
        if (boxSize < 4) {
            // has to form square at minimum to be considered a viable winning pattern
            return false;
        }
        TicTacToeSpace[] spacesToCheck = new TicTacToeSpace[boxSize];
        double lastSpaceToCheck = (BOARD_SIZE * BOARD_SIZE) - (((boxLengthSize - 1) * BOARD_SIZE) + boxLengthSize);
        for (int i = 0; i <= lastSpaceToCheck; i++) {
            if (i % BOARD_SIZE < (i + boxLengthSize - 1) % BOARD_SIZE) {
                for (int j = 0; j < boxSize; j++) {
                    double index = i
                            + (Math.floor(j / boxLengthSize) * BOARD_SIZE)
                            + (j % boxLengthSize);
//                    Log.d(TAG, "Index = " + index
//                            + "\ni=" + i
//                            + "\nj=" + j
//                            + "\nlastSpaceToCheck=" + lastSpaceToCheck
//                            + "\nBOARD_SIZE=" + BOARD_SIZE
//                            + "\nboxLengthSize=" + boxLengthSize
//                            + "\nboxSize=" + boxSize
//                    );
                    spacesToCheck[j] = state.get((int) index);
//                    Log.d(TAG, "Added index: " + index + "\nWith a value of " + spacesToCheck[j].getMarked().toString());
                }
                if (allTheSameAndNotUnmarked(spacesToCheck)) {
                    Log.d(TAG, "Box win!");
                    return true;
                }
//                Log.d(TAG, "No box win.");
            }
        }
        return false;
    }


    private static boolean allTheSameAndNotUnmarked(TicTacToeSpace... spaces) {
        TicTacToeSpace.MarkedState target = spaces[0].getMarked();
        if (target == TicTacToeSpace.MarkedState.UNMARKED) {
            return false;
        }

        for (TicTacToeSpace space : spaces) {
            if (space.getMarked() != target) {
                return false;
            }
        }
        return true;
    }
}
