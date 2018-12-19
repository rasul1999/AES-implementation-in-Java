package com.nr.encryption;

import com.nr.helpers.GaloisField;
import com.nr.helpers.OperationType;
import com.nr.helpers.SBox;
import com.nr.helpers.State;
import com.nr.key_generation.Key;


public class AesRound {

    protected State keyState, currentState;
    protected int[][] diffusionMatrix;
    protected static int roundCount = 0;

    protected AesRound() {}

    public AesRound(State plainTextState, Key key) {

        System.out.println("In AES round " + roundCount);

        keyState = key.getState();
        currentState = plainTextState;

        diffusionMatrix = new int[][] {
                {2, 3, 1, 1},
                {1, 2, 3, 1},
                {1, 1, 2, 3},
                {3, 1, 1, 2}
        };

        if (roundCount == 0) {
            addRoundKey();
        }
        SBox.substituteBytes(currentState, OperationType.ENCRYPTION);
        shiftRows();
        if (roundCount != 9) {
            mixColumns();
        }
        key.switchToNextKey();
        addRoundKey();
        roundCount++;
        if (roundCount == 10) {
            roundCount = 0;
        }
        currentState.printState();
    }

    protected void addRoundKey() {

        int[][] textStateMatrix = currentState.getStateMatrix();
        int[][] keyStateMatrix = keyState.getStateMatrix();


        for (int i = 0; i < textStateMatrix.length; i++) {

            for (int j = 0; j < textStateMatrix[0].length; j++) {

                textStateMatrix[i][j] ^= keyStateMatrix[i][j];
            }
        }
        currentState.updateState(State.Changed.STATE_MATRIX);
    }

    protected void shiftRows() {

        int[][] currentStateMatrix = currentState.getStateMatrix();

        for (int i = 1; i < 4; i++) {

            int[] row = new int[4];

            for (int j = i; j < 4; j++) {
                row[j - i] = currentStateMatrix[i][j];
            }
            int counter = 0;
            for (int j = 4 - i; j < 4; j++) {
                row[j] = currentStateMatrix[i][counter++];
            }
            currentStateMatrix[i] = row;
        }
        currentState.updateState(State.Changed.STATE_MATRIX);
    }

    protected void mixColumns() {

        int[][] currentStateMatrix = currentState.getStateMatrix();

        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 4; j++) {

                int sum = 0;
                for (int g = 0; g < 4; g++) {

                    sum ^= GaloisField.multiply(currentStateMatrix[g][i], diffusionMatrix[j][g]);
                }
                currentStateMatrix[j][i] = sum;
            }
        }
        currentState.updateState(State.Changed.STATE_MATRIX);
    }
}
