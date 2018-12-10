package com.nr.encryption;

import com.nr.helpers.GaloisField;
import com.nr.helpers.State;


public class AesRound {

    private State plainTextState, keyState, currentState;
    private byte[][] diffusionMatrix;

    public AesRound(State plainTextState, State keyState) {

        this.keyState = keyState;
        this.plainTextState = plainTextState;

        diffusionMatrix = new byte[][] {
                {2, 3, 1, 1},
                {1, 2, 3, 1},
                {1, 1, 2, 3},
                {3, 1, 1, 2}
        };
    }

    private void addRoundKey() {

        byte[][] textStateMatrix = plainTextState.getStateMatrix();
        byte[][] keyStateMatrix = keyState.getStateMatrix();
        byte[][] currentStateMatrix = new byte[4][4];

        byte[] currentBlockArray = new byte[16];
        int counter = 0;

        for (int i = 0; i < currentStateMatrix.length; i++) {

            for (int j = 0; j < currentStateMatrix[0].length; j++) {

                currentBlockArray[counter++] = (byte)(textStateMatrix[i][j] ^ keyStateMatrix[i][j]);
            }
        }
        currentState = new State(currentBlockArray, (byte)0);
    }

    private void shiftRows() {

        byte[][] currentStateMatrix = currentState.getStateMatrix();

        for (int i = 1; i < 4; i++) {

            byte[] row = new byte[4];

            for (int j = i; j < 4; j++) {
                row[i - j] = currentStateMatrix[i][j];
            }
            int counter = 0;
            for (int j = 4 - i; j < 4; j++) {
                row[j] = currentStateMatrix[i][counter++];
            }
        }
        currentState.updateState(State.Changed.STATE_MATRIX);
    }

    private void mixColumns() {

        byte[][] currentStateMatrix = currentState.getStateMatrix();

        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 4; j++) {

                byte sum = 0;
                for (int g = 0; g < 4; g++) {

                    sum ^= GaloisField.multiply(currentStateMatrix[g][i], diffusionMatrix[j][g]);
                }
                currentStateMatrix[j][i] = sum;
            }
        }
        currentState.updateState(State.Changed.STATE_MATRIX);
    }
}
