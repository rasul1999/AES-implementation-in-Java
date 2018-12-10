package com.nr.key_generation;


import com.nr.helpers.HexByte;
import com.nr.helpers.SBox;
import com.nr.helpers.State;
import com.nr.helpers.Word;


public class Key {

    private State keyState;
    private byte roundCount;
    private static final byte[] rcon = new byte[] {
            0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, (byte)0x80, 0x1B, 0x36
    };

    Key(byte[] keyBytes) {
        keyState = new State(keyBytes, (byte)0);
        roundCount = 0;
    }

    private void rotateWord(Word w) {

        HexByte firstByte = w.get(0);

        for (int i = 0; i < 3; i++) {
            w.set(i, w.get(i + 1));
        }
        w.set(4, firstByte);
    }

    private void substituteWord(Word w) {

        byte[][] sBoxMatrix = SBox.getSBox().getSBoxMatrix();

        for (int i = 0; i < 4; i++) {
            byte row = w.get(i).row;
            byte col = w.get(i).column;
            w.set(i, new HexByte(sBoxMatrix[row][col]));
        }
    }

    private Word xor(Word w1, Word w2) {
        // TODO: implement this method

        return null; // TODO: delete this line
    }


    public State getState() {
        return keyState;
    }


    public void switchToNextKey() {

        byte[][] keyMatrix = keyState.getStateMatrix();
        Word[] words = keyState.getWordVector();

        rotateWord(words[3]);
        substituteWord(words[3]);
        // TODO: please write something more readable instead, for god's sake:
        words[3].set(0, new HexByte((byte)(words[3].get(0).getByte() ^ rcon[roundCount++])));


        keyState.updateState(State.Changed.WORD_VECTOR);
    }
}
