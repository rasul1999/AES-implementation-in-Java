package com.nr.helpers;

import java.util.ArrayList;


/**
 * byteArray is byte representation of plain text.
 */

public class Segmentation {

    private static ArrayList<State> states = new ArrayList<>();

    public static ArrayList<State> toStates(byte[] byteArray) {

        int startIndex = 0;
        int endIndex = 16;

        while (endIndex <= byteArray.length) {

            byte[] stateArray = new byte[16];
            int counter = 0;

            for (int i = startIndex; i < endIndex; i++) {
                stateArray[counter++] = byteArray[i];
            }

            states.add(new State(stateArray));

            startIndex += 16;
            endIndex += 16;
        }

        if (endIndex - 16 != byteArray.length) {

            byte[] stateArray = new byte[16];
            int counter = 0;

            for (int i = startIndex; i < byteArray.length; i++) {
                stateArray[counter++] = byteArray[i];
            }

            byte b = 0;

            for (int i = counter; i < 16; i++) {
                stateArray[i] = b;
            }

            states.add(new State(stateArray));
        }

        return states;
    }
}
