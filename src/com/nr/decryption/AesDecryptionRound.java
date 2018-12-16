package com.nr.decryption;

import com.nr.encryption.AesRound;
import com.nr.helpers.OperationType;
import com.nr.helpers.SBox;
import com.nr.helpers.State;
import com.nr.key_generation.Key;


public class AesDecryptionRound extends AesRound {

    public AesDecryptionRound(State cipherTextState, Key key) {

        System.out.println("In AES decryption round: " + roundCount);

        keyState = key.getState();
        currentState = cipherTextState;

        diffusionMatrix = new int[][] {
                {14, 11, 13, 9},
                {9, 14, 11, 13},
                {13, 9, 14, 11},
                {11, 13, 9, 14}
        };

        if (roundCount == 0) {
            addRoundKey();
        }
        shiftRows();
        SBox.substituteBytes(currentState, OperationType.DECRYPTION);
        key.switchToNextKey();
        addRoundKey();
        if (roundCount != 9) {
            mixColumns();
        }
        roundCount++;
        if (roundCount == 10) {
            roundCount = 0;
        }
    }

    @Override
    protected void shiftRows() {

        int[][] currentStateMatrix = currentState.getStateMatrix();

        for (int i = 1; i < 4; i++) {

            int[] row = new int[4];

            for (int j = 0; j < 4 - i; j++) {

                row[j + i] = currentStateMatrix[i][j];
            }
            int counter = 0;
            for (int j = 4 - i; j < 4; j++) {

                row[counter++] = currentStateMatrix[i][j];
            }
            currentStateMatrix[i] = row;
        }
        currentState.updateState(State.Changed.STATE_MATRIX);
    }
}
