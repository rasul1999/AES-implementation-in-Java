package com.nr;

import com.nr.encryption.Encryptor;

import java.io.*;


public class Main {

    private static int[] charToBytes(char c) {

        System.out.println("In charToBytes"); //TODO: delete line
        int intValue = (int)c;
        int lowByte = 0;
        int highByte = 0;

        int counter = 0;
        for (int i = 7; i >= 0; i--) {
            highByte += Math.pow(2, counter++) * (intValue % 2);
            intValue /= 2;
        }
        counter = 0;
        for (int i = 7; i >= 0; i--) {
            lowByte += Math.pow(2, counter++) * (intValue % 2);
            intValue /= 2;
        }
        return new int[] {lowByte, highByte};
    }

    private static char bytesToChar(int[] bytes) {

        System.out.println("In bytesToChar"); //TODO: delete line
        return (char)(bytes[0] * 512 + bytes[1]);
    }

    private static int[] charToByteArray(char[] charArray) {

        System.out.println("In charToByteArray"); //TODO: delete line
        int[] ret = new int[charArray.length * 2];
        int counter = 0;

        for (int i = 0; i < charArray.length; i++) {

            ret[counter++] = charToBytes(charArray[i])[0];
            ret[counter++] = charToBytes(charArray[i])[1];
        }
        return ret;
    }


    private static char[] byteToCharArray(int[] byteArray) {

        System.out.println("In byteToCharArray"); //TODO: delete line
        char[] ret = new char[byteArray.length / 2];
        int counter = 0;

        for (int i = 0; i < byteArray.length; i += 2) {

            int[] bytes = new int[] {byteArray[i], byteArray[i + 1]};
            ret[counter++] = bytesToChar(bytes);
        }
        return ret;
    }


    public static void main(String[] args) {

        String plainText = "", keyString = "";

        try {

            BufferedReader reader = new BufferedReader(
                    new FileReader(new File(Resources.getProperty("path_to_plain_text")))
            );
            String line;
            while ((line = reader.readLine()) != null) {
                plainText += line;
            }

            reader = new BufferedReader(
                    new FileReader(new File(Resources.getProperty("path_to_key")))
            );
            while ((line = reader.readLine()) != null) {
                keyString += line;
            }

        } catch (IOException e) {

            System.out.printf("Plain text file not found");
            e.printStackTrace();
        }

        System.out.println(plainText);// TODO: delete this line
        System.out.println(keyString);// TODO: delete this line

        char[] plainTextCharArray = plainText.toCharArray();
        char[] keyCharArray = keyString.toCharArray();


        int[] plainTextArray = charToByteArray(plainTextCharArray);
        int[] keyArray = charToByteArray(keyCharArray);

        Encryptor aesEncryptor = new Encryptor(plainTextArray, keyArray);

        try {

            PrintWriter writer = new PrintWriter(Resources.getProperty("path_to_cipher_text"));
            String resultString = String.valueOf(byteToCharArray(aesEncryptor.getCipherTextBytes()));
            writer.write(resultString);
            writer.flush();
        }
        catch (FileNotFoundException e) {
            System.out.println("Cipher text file not found");
            e.printStackTrace();
        }

    }
}
