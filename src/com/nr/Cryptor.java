package com.nr;

import com.nr.decryption.AesDecryptionRound;
import com.nr.encryption.AesRound;
import com.nr.helpers.OperationType;
import com.nr.helpers.Segmentation;
import com.nr.helpers.State;
import com.nr.key_generation.Key;

import java.util.ArrayList;

public class Cryptor {

    private int[] resultBytes;

    public Cryptor(int[] plainTextBytes, int[] keyBytes, OperationType operationType) {

        ArrayList<State> states = Segmentation.toStates(plainTextBytes);
        int[] properKeyBytes = new int[16];
        int keyLen = (keyBytes.length > 16 ? 16 : keyBytes.length);

        for (int i = 0; i < keyLen; i++) {

            properKeyBytes[i] = keyBytes[i];
        }
        for (int i = keyLen; i < 16; i++) {

            properKeyBytes[i] = 0;
        }

        for (int i = 0; i < states.size(); i++) {
            System.out.println("In block " + (i + 1) + " **********************");
            Key key = new Key(properKeyBytes, operationType);

            for (int j = 0; j < 10; j++) {

                if (operationType == OperationType.ENCRYPTION) {
                    new AesRound(states.get(i), key);
                }
                else {
                    new AesDecryptionRound(states.get(i), key);
                }

            }
        }
        resultBytes = Segmentation.fromStatesToBytes(states);
    }


    public int[] getResultBytes() {
        return resultBytes;
    }
}
