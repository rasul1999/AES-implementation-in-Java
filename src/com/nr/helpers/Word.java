package com.nr.helpers;


/**
 * A Word, containing four bytes, is one row of state matrix
 */
public class Word {

    private HexByte[] hexBytes = new HexByte[4];
    private int[] bytes = new int[4];

    public HexByte get(int index) {
        if (index > 3) throw new IndexOutOfBoundsException("A word has only 4 bytes");
        return hexBytes[index];
    }

    public void set(int index, HexByte hexByte) {
        if (index > 3) throw new IndexOutOfBoundsException("A word has only 4 bytes");
        hexBytes[index] = hexByte;
    }

    public Word(int[] bytes) {

        for (int i = 0; i < 4; i++) {
            hexBytes[i] = new HexByte(bytes[i]);
            this.bytes[i] = bytes[i];
        }
    }

    public int[] getBytes() {
        return bytes;
    }
}
