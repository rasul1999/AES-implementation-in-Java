package com.nr.helpers;

import java.util.ArrayList;

public class HexByte {

    public final int row, column;
    private int theByte;

    public HexByte(int b) {

        theByte = Integer.valueOf(b);

        ArrayList<Integer> list = new ArrayList<>();

        while (b > 0) {
            list.add(b % 2);
            b /= 2;
        }
        for (int i = list.size(); i < 8; i++) {
            list.add(0);
        }

        ArrayList<Integer> rlist = new ArrayList<>();

        for (int i = list.size() - 1; i >= 0; i--) {
            rlist.add(list.get(i));
        }

        int _row = 0, _column = 0;
        for (int i = 0; i < 4; i++) {
            _row += Math.pow(2, 3 - i) * rlist.get(i);
        }

        for (int i = 4; i < rlist.size(); i++) {
            _column += Math.pow(2, 7 - i) * rlist.get(i);
        }
        row = _row;
        column = _column;
    }

    public int getByte() {
        return theByte;
    }
}
