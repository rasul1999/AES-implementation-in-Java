package com.nr.helpers;

import java.util.ArrayList;


/**
 * byteArray is byte representation of plain text.
 */
public class Segmentation {

    private static ArrayList<State> states = new ArrayList<>();

    public static int[] fromStatesToBytes(ArrayList<State> states) {

        int[] byteArray = new int[states.size() * 16];

        int counter = 0;

        for (int i = 0; i < states.size(); i++) {

            int[] stateByteArray = states.get(i).toByteArray();

            for (int j = 0; j < stateByteArray.length; j++) {
                byteArray[counter++] = stateByteArray[j];
            }
        }
        return byteArray;
    }

    public static ArrayList<State> toStates(int[] intArray) {

        states.clear();

        int startIndex = 0;
        int endIndex = 16;

        while (endIndex <= intArray.length) {

            int[] stateArray = new int[16];
            int counter = 0;

            for (int i = startIndex; i < endIndex; i++) {
                stateArray[counter++] = intArray[i];
            }

            states.add(new State(stateArray));

            startIndex += 16;
            endIndex += 16;
        }

        if (startIndex != intArray.length) {

            int[] stateArray = new int[16];
            int counter = 0;

            for (int i = startIndex; i < intArray.length; i++) {
                stateArray[counter++] = intArray[i];
            }

            for (int i = counter; i < 16; i++) {
                stateArray[i] = 0;
            }

            states.add(new State(stateArray));
        }

        return states;
    }
}
