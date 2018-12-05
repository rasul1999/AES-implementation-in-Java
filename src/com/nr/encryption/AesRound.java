package com.nr.encryption;

import com.nr.helpers.State;

public class AesRound {

    private State plainTextState, keyState, currentState;

    public AesRound(State plainTextState, State keyState) {

        this.keyState = keyState;
        this.plainTextState = plainTextState;
    }

    public void addRoundKey() {

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
        currentState = new State(currentBlockArray);
    }


}
