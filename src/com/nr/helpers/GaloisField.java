package com.nr.helpers;


public class GaloisField {

    // Irreducible polynomial (x^4 + 1)
    private static final byte POL = 0x11;

    public static byte multiply(byte a, byte b) {

        byte ret = 0;

        while (a != 0 && b != 0) {

            if ((b & 1) != 0) ret ^= a;
            if ((a & 0x80) != 0) a = (byte)((a << 1) ^ POL);
            else a <<= 1;
            b >>= 1;
        }
        return ret;
    }
}
