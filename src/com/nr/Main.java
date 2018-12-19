package com.nr;

import com.nr.helpers.Convert;
import com.nr.helpers.OperationType;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;


public class Main {

    public static void main(String[] args) throws Exception {

        String plainText = "", keyString = "";

        try {

            BufferedReader reader = new BufferedReader(
                    new FileReader(new File(Resources.getProperty("plain_text_path")))
            );
            String line;
            while ((line = reader.readLine()) != null) {
                plainText += line;
            }

            reader = new BufferedReader(
                    new FileReader(new File(Resources.getProperty("key_path")))
            );
            while ((line = reader.readLine()) != null) {
                keyString += line;
            }

        } catch (IOException e) {

            System.out.println("Plain text file not found");
            e.printStackTrace();
        }

        char[] plainTextCharArray = plainText.toCharArray();
        char[] keyCharArray = keyString.toCharArray();


        int[] plainTextArray = Convert.charToByteArray(plainTextCharArray);
        int[] keyArray = Convert.charToByteArray(keyCharArray);

        Cryptor aesEncryptor = new Cryptor(plainTextArray, keyArray, OperationType.ENCRYPTION);

        try {

            PrintWriter writer = new PrintWriter(Resources.getProperty("cipher_text_path"));
            String resultString = String.valueOf(Convert.byteToCharArray(aesEncryptor.getResultBytes()));
            writer.write(resultString);
            writer.flush();

            Cryptor aesDecryptor = new Cryptor(
                    Convert.charToByteArray(plainText.toCharArray()),
                    Convert.charToByteArray(keyString.toCharArray()),
                    OperationType.DECRYPTION
            );

            writer = new PrintWriter(Resources.getProperty("decrypted_text_path"));
            writer.write(String.valueOf(Convert.byteToCharArray(plainTextArray)));
            writer.flush();

        } catch (FileNotFoundException e) {
            System.out.println("Cipher text file not found");
            e.printStackTrace();
        }

        {// TODO: image encryption - decryption
            BufferedImage image = ImageIO.read(new File(Resources.getProperty("image_path")));
            int[] imageArray = Convert.imageToByteArray(image);

            Cryptor imageEncryptor = new Cryptor(imageArray, keyArray, OperationType.ENCRYPTION);

            int[] cipherImageArray = imageEncryptor.getResultBytes();

            BufferedImage cipherImage = Convert.byteArrayToImage(cipherImageArray, image.getWidth(), image.getHeight());

            ImageIO.write(cipherImage, "png", new File(Resources.getProperty("cipher_image_path")));

            BufferedImage encryptedImage = ImageIO.read(new File(Resources.getProperty("cipher_image_path")));
            Cryptor imageDecryptor = new Cryptor(
                    Convert.imageToByteArray(encryptedImage), keyArray, OperationType.DECRYPTION
            );
            decryptedImage = Convert.byteArrayToImage(imageArray, image.getWidth(), image.getHeight());
            ImageIO.write(decryptedImage, "png", new File(Resources.getProperty("decrypted_image_path")));
        }
    }
}
