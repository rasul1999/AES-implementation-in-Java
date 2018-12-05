package com.nr.helpers;

import java.util.ArrayList;

public class HexByte {

    public byte first = 0, second = 0;

    public HexByte(byte b) {

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
            first += Math.pow(2, 3 - i);
        }

        for (int i = 4; i < rlist.size(); i++) {
            second += Math.pow(2, 7 - i);
        }
    }
}
