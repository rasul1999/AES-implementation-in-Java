package com.nr.helpers;

import com.nr.Resources;

import java.io.*;


/**
 * SBox is a table that is used in Byte Substitution phase of AES algorithm
 */
public class SBox {

    private int[][] sBoxMatrix;
    private static SBox sBox = null;
    private static SBox inverseSBox = null;

    private SBox(File file) {

        sBoxMatrix = new int[16][16];

        int counter = 0;

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = reader.readLine()) != null) {

                String[] hexArr = line.split("\\s");
                for (int i = 0; i < hexArr.length; i++) {
                    sBoxMatrix[counter][i] = Integer.parseInt(hexArr[i], 16);
                }
                counter++;
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("SBox file not found");
        }
        catch (IOException e) {
            System.out.println("Error while reading SBox file");
        }
    }

    public static SBox getSBox(OperationType type) {

        if (type == OperationType.ENCRYPTION) {

            if (sBox == null) {

                String fileName = Resources.getProperty("sbox_path");
                sBox = new SBox(new File(fileName));
            }
            return sBox;
        }
        else {

            if (inverseSBox == null) {

                String fileName = Resources.getProperty("inv_sbox_path");
                inverseSBox = new SBox(new File(fileName));
            }
            return inverseSBox;
        }
    }

    public int[][] getSBoxMatrix() {
        return sBoxMatrix;
    }


    public static void substituteBytes(State currentState, OperationType operationType) {

        int[][] sBoxMatrix = getSBox(operationType).getSBoxMatrix();
        int[][] currentStateMatrix = currentState.getStateMatrix();
        Word[] currentStateWordVector = currentState.getWordVector();

        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 4; j++) {

                int row = currentStateWordVector[i].get(j).row;
                int col = currentStateWordVector[i].get(j).column;
//                System.out.printf("Row: %d, Col: %d | ", row, col);

                currentStateMatrix[i][j] = sBoxMatrix[row][col];
            }
        }
        currentState.updateState(State.Changed.STATE_MATRIX);
    }
}
