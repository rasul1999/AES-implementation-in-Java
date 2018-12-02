package com.nr.helpers;

public class State {

    private byte[][] stateMatrix;

    public State(byte[] blockArray) {

        stateMatrix = new byte[4][4];

        int counter = 0;

        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 4; j++) {

                stateMatrix[i][j] = blockArray[counter++];
            }
        }
    }

    public byte[][] getstateMatrix() {
        return stateMatrix;
    }
}
