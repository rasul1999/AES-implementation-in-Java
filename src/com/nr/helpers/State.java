package com.nr.helpers;

/**
 * State is two dimensional(4x4) array of bytes. All operations in AES are performed on a state.
 */
public class State {

    private byte[][] stateMatrix;
    private Word[] wordVector;

    public State(byte[] blockArray) {

        stateMatrix = new byte[4][4];

        int counter = 0;

        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 4; j++) {

                stateMatrix[i][j] = blockArray[counter++];
            }
        }

        wordVector = new Word[4];

        for (int i = 0; i < stateMatrix.length; i++) {
            wordVector[i] = new Word(stateMatrix[i]);
        }
    }

    public byte[][] getStateMatrix() {
        return stateMatrix;
    }

    public Word[] getWordVector() {
        return wordVector;
    }
}
