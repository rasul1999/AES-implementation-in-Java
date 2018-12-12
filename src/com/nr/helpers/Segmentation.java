package com.nr.helpers;

import java.util.ArrayList;


/**
 * byteArray is byte representation of plain text.
 */


public class Segmentation {

    private static ArrayList<State> states = new ArrayList<>();

    public static int[] fromStatesToBytes(ArrayList<State> states) {

        System.out.println("In fromStatesToBytes"); //TODO: delete line
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

        System.out.println("In toStates"); //TODO: delete line
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

        if (endIndex - 16 != intArray.length) {

            int[] stateArray = new int[16];
            int counter = 0;

            for (int i = startIndex; i < intArray.length; i++) {
                stateArray[counter++] = intArray[i];
            }

            int b = 0;

            for (int i = counter; i < 16; i++) {
                stateArray[i] = b;
            }

            states.add(new State(stateArray));
        }

        return states;
    }
}
