package com.nr.helpers;

import java.io.*;


/**
 * SBox is a table that is used in Byte Substitution phase of AES algorithm
 */
public class SBox {

    byte[][] sBoxMatrix;

    public SBox(File file) {

        sBoxMatrix = new byte[16][16];
        int counter = 0;

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = reader.readLine()) != null) {

                String[] hexArr = line.split("\\s");
                for (int i = 0; i < hexArr.length; i++) {
                    sBoxMatrix[counter][i] = Byte.parseByte(hexArr[i], 16);
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
}
