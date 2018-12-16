package com.nr.helpers;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Convert {

    private static int[] charToBytes(char c) {

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

        return (char)(bytes[0] * 256 + bytes[1]);
    }

    public static int[] charToByteArray(char[] charArray) {

        int[] ret = new int[charArray.length * 2];
        int counter = 0;

        for (int i = 0; i < charArray.length; i++) {

            ret[counter++] = charToBytes(charArray[i])[0];
            ret[counter++] = charToBytes(charArray[i])[1];
        }
        return ret;
    }


    public static char[] byteToCharArray(int[] byteArray) {

        char[] ret = new char[byteArray.length / 2];
        int counter = 0;

        for (int i = 0; i < byteArray.length; i += 2) {

            int[] bytes = new int[] {byteArray[i], byteArray[i + 1]};
            ret[counter++] = bytesToChar(bytes);
        }
        return ret;
    }


    public static int[] imageToByteArray(BufferedImage image) {

        int height = image.getHeight();
        int width = image.getWidth();

        int[] ret = new int[width * height * 4];
        int counter = 0;

        for (int i = 0; i < width - 1; i += 2) {
            for (int j = 0; j < height - 1; j += 2) {

                int rgb1 = image.getRGB(i, j);
                ret[counter++] = (rgb1 >> 24) & 0xFF;
                ret[counter++] = (rgb1 >> 16) & 0xFF;
                ret[counter++] = (rgb1 >> 8) & 0xFF;
                ret[counter++] = rgb1 & 0xFF;

                int rgb2 = image.getRGB(i + 1, j);
                ret[counter++] = (rgb2 >> 24) & 0xFF;
                ret[counter++] = (rgb2 >> 16) & 0xFF;
                ret[counter++] = (rgb2 >> 8) & 0xFF;
                ret[counter++] = rgb2 & 0xFF;

                int rgb3 = image.getRGB(i, j + 1);
                ret[counter++] = (rgb3 >> 24) & 0xFF;
                ret[counter++] = (rgb3 >> 16) & 0xFF;
                ret[counter++] = (rgb3 >> 8) & 0xFF;
                ret[counter++] = rgb3 & 0xFF;

                int rgb4 = image.getRGB(i + 1, j + 1);
                ret[counter++] = (rgb4 >> 24) & 0xFF;
                ret[counter++] = (rgb4 >> 16) & 0xFF;
                ret[counter++] = (rgb4 >> 8) & 0xFF;
                ret[counter++] = rgb4 & 0xFF;
            }
        }
        if (width % 2 == 1) {
            for (int i = 0; i < height; i++) {

                int rgb = image.getRGB(width - 1, i);
                ret[counter++] = (rgb >> 24) & 0xFF;
                ret[counter++] = (rgb >> 16) & 0xFF;
                ret[counter++] = (rgb >> 8) & 0xFF;
                ret[counter++] = rgb & 0xFF;
            }
        }
        if (height % 2 == 1) {
            for (int i = 0; i < width; i++) {

                int rgb = image.getRGB(i, height - 1);
                ret[counter++] = (rgb >> 24) & 0xFF;
                ret[counter++] = (rgb >> 16) & 0xFF;
                ret[counter++] = (rgb >> 8) & 0xFF;
                ret[counter++] = rgb & 0xFF;
            }
        }
        return ret;
    }

    public static BufferedImage byteArrayToImage(int[] arr, int width, int height) {

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        int counter = 0;

        for (int i = 0; i < width - 1; i += 2) {
            for (int j = 0; j < height - 1; j += 2) {

                int color = (arr[counter++] << 24) | (arr[counter++] << 16) | (arr[counter++] << 8) | arr[counter++];
                image.setRGB(i, j, color);

                color = (arr[counter++] << 24) | (arr[counter++] << 16) | (arr[counter++] << 8) | arr[counter++];
                image.setRGB(i + 1, j, color);

                color = (arr[counter++] << 24) | (arr[counter++] << 16) | (arr[counter++] << 8) | arr[counter++];
                image.setRGB(i, j + 1, color);

                color = (arr[counter++] << 24) | (arr[counter++] << 16) | (arr[counter++] << 8) | arr[counter++];
                image.setRGB(i + 1, j + 1, color);
            }
        }

        if (width % 2 == 1) {
            for (int i = 0; i < height; i++) {

                int color = (arr[counter++] << 24) | (arr[counter++] << 16) | (arr[counter++] << 8) | arr[counter++];
                image.setRGB(width - 1, i, color);
            }
        }

        if (height % 2 ==  1) {
            for (int i = 0; i < width; i++) {

                int color = (arr[counter++] << 24) | (arr[counter++] << 16) | (arr[counter++] << 8) | arr[counter++];
                image.setRGB(i, height - 1, color);
            }
        }

        return image;
    }
}
