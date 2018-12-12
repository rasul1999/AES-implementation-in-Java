package com.nr.encryption;

import com.nr.helpers.GaloisField;
import com.nr.helpers.SBox;
import com.nr.helpers.State;
import com.nr.key_generation.Key;


public class AesRound {

    private State keyState, currentState;
    private int[][] diffusionMatrix;
    private static int roundCount = 0;


    public AesRound(State plainTextState, Key key) throws Exception {

        System.out.println("In AesRound constructor"); //TODO: delete line
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
        SBox.substituteBytes(currentState);
        shiftRows();
        if (roundCount != 9) {
            mixColumns();
        }
        key.switchToNextKey();
        addRoundKey();
        if (roundCount == 9) {
            roundCount = 0;
        }
    }

    private void addRoundKey() {

        System.out.println("In addRoundKey"); //TODO: delete line
        int[][] textStateMatrix = currentState.getStateMatrix();
        int[][] keyStateMatrix = keyState.getStateMatrix();


        for (int i = 0; i < textStateMatrix.length; i++) {

            for (int j = 0; j < textStateMatrix[0].length; j++) {

                textStateMatrix[i][j] = textStateMatrix[i][j] ^ keyStateMatrix[i][j];
            }
        }
    }

    private void shiftRows() {

        System.out.println("In shiftRows"); //TODO: delete line
        int[][] currentStateMatrix = currentState.getStateMatrix();

        for (int i = 1; i < 4; i++) {

            int[] row = new int[4];

            for (int j = i; j < 4; j++) {
                row[j - i] = currentStateMatrix[i][j];// TODO: this is not right
            }
            int counter = 0;
            for (int j = 4 - i; j < 4; j++) {
                row[j] = currentStateMatrix[i][counter++];
            }
        }
        currentState.updateState(State.Changed.STATE_MATRIX);
    }

    private void mixColumns() {

        System.out.println("In mixColumns"); //TODO: delete line
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
