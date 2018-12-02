package com.nr.helpers;

import java.util.ArrayList;


/**
 * byteArray is byte representation of plain text.
 *
 * State is two dimensional(4x4) array of bytes. All operations in AES are performed on a state.
 */

public class Segmentation {

    public static ArrayList<State> toStates(byte[] byteArray) {

        ArrayList<State> states = new ArrayList<>();

        int startIndex = 0;
        int endIndex = 128;

        while (endIndex <= byteArray.length) {

            byte[] stateArray = new byte[128];
            int counter = 0;

            for (int i = startIndex; i < endIndex; i++) {
                stateArray[counter++] = byteArray[i];
            }

            states.add(new State(stateArray));

            startIndex += 128;
            endIndex += 128;
        }

        if (endIndex - 128 != byteArray.length) {

            byte[] stateArray = new byte[128];
            int counter = 0;

            for (int i = startIndex; i < byteArray.length; i++) {
                stateArray[counter++] = byteArray[i];
            }

            byte b = 0;

            for (int i = counter; i < 128; i++) {
                stateArray[i] = b;
            }

            states.add(new State(stateArray));
        }

        return states;
    }
}
