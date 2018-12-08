package com.nr.helpers;

import java.util.ArrayList;

public class HexByte {

    public byte row = 0, column = 0;
    private byte theByte;

    public HexByte(byte b) {

        theByte = b;

        ArrayList<Byte> list = new ArrayList<>();

        while (b > 0) {
            list.add((byte)(b % 2));
            b /= 2;
        }

        ArrayList<Byte> rlist = new ArrayList<>();

        for (int i = list.size() - 1; i >= 0; i--) {
            rlist.add(list.get(i));
        }


        for (int i = 0; i < 4; i++) {
            row += Math.pow(2, 3 - i);
        }

        for (int i = 4; i < rlist.size(); i++) {
            column += Math.pow(2, 7 - i);
        }
    }

    public byte getByte() {
        return theByte;
    }
}
