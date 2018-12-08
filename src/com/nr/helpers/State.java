package com.nr.helpers;

/**
 * State is two dimensional(4x4) array of bytes. All operations in AES are performed on a state.
 */
public class State {

    public enum Changed {
        STATE_MATRIX,
        WORD_VECTOR
    }

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

        updateWordVector();
    }

    private void updateWordVector() {

        wordVector = new Word[4];

        for (int i = 0; i < 4; i++) {
            wordVector[i] = new Word(stateMatrix[i]);
        }
    }

    private void updateStateMatrix() {

        stateMatrix = new byte[4][4];

        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 4; j++) {

                stateMatrix[i][j] = wordVector[i].get(j).getByte();
            }
        }
    }

    public void updateState(Changed changed) {

        if (changed == Changed.STATE_MATRIX) {
            updateWordVector();
        }
        else if (changed == Changed.WORD_VECTOR) {
            updateStateMatrix();
        }
    }

    public byte[][] getStateMatrix() {
        return stateMatrix;
    }

    public Word[] getWordVector() {
        return wordVector;
    }
}
