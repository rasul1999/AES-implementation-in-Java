package com.nr.helpers;


/**
 * A Word, containing four bytes, is one row of state matrix
 */
public class Word {

    private HexByte[] hexBytes = new HexByte[4];

    public HexByte[] get(int index) {
        if (index > 3) throw new ArrayIndexOutOfBoundsException("A word has only 4 bytes");
        return hexBytes;
    }

    public Word(byte[] bytes) {

        for (int i = 0; i < 4; i++) {
            hexBytes[i] = new HexByte(bytes[i]);
        }
    }
}
