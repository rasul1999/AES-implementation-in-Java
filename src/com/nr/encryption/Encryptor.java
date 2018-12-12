package com.nr.encryption;

import com.nr.helpers.Segmentation;
import com.nr.helpers.State;
import com.nr.key_generation.Key;

import java.util.ArrayList;

public class Encryptor {

    private int[] cipherTextBytes;

    public Encryptor(int[] plainTextBytes, int[] keyBytes) {

        System.out.println("In Encryptor constructor"); //TODO: delete line
        ArrayList<State> states = Segmentation.toStates(plainTextBytes);
        int[] properKeyBytes = new int[16];
        int keyLen = (keyBytes.length > 16 ? 16 : keyBytes.length);

        for (int i = 0; i < keyLen; i++) {

            properKeyBytes[i] = keyBytes[i];
        }
        for (int i = keyLen; i < 16; i++) {

            properKeyBytes[i] = 0;
        }

        Key key = new Key(properKeyBytes);

        for (int i = 0; i < states.size(); i++) {

            for (int j = 0; j < 10; j++) {

                try {
                    new AesRound(states.get(i), key);
                    states.get(i).printState();
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        cipherTextBytes = Segmentation.fromStatesToBytes(states);
    }

    public int[] getCipherTextBytes() {
        return cipherTextBytes;
    }
}
