package com.nr.helpers;


public class GaloisField {

    // Irreducible polynomial (x^4 + 1)
    private static final int POL = 0x11;

    public static int multiply(int a, int b) {

        System.out.println("In GaloisField multiply"); //TODO: delete line
        int ret = 0;

        while (a != 0 && b != 0) {

            if ((b & 1) != 0) {
                ret ^= a;
            }
            if ((a & 0x80) != 0) {
                a = (((a << 1) & 0xFF) ^ POL);
            }
            else {
                a = (a << 1) & 0xFF;
            }
            b = (b >> 1) & 0xFF;
        }
        return ret;
    }
}
