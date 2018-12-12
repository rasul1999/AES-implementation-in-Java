package com.nr.key_generation;


import com.nr.helpers.HexByte;
import com.nr.helpers.SBox;
import com.nr.helpers.State;
import com.nr.helpers.Word;


public class Key {

    private State keyState;
    private int roundCount;
    private static final int[] rcon = new int[] {
            0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1B, 0x36
    };

    public Key(int[] keyBytes) {
        keyState = new State(keyBytes);
        roundCount = 0;
    }

    private void rotateWord(Word w) {

        System.out.println("In rotateWord"); //TODO: delete line
        HexByte firstByte = w.get(0);// TODO: not right

        for (int i = 0; i < 3; i++) {
            w.set(i, w.get(i + 1));
        }
        w.set(3, firstByte);
    }

    private void substituteWord(Word w) {

        System.out.println("In substituteWord"); //TODO: delete line
        int[][] sBoxMatrix = SBox.getSBox().getSBoxMatrix();

        for (int i = 0; i < 4; i++) {
            int row = w.get(i).row;
            int col = w.get(i).column;
            w.set(i, new HexByte(sBoxMatrix[row][col]));
        }
    }

    private Word xor(Word w1, Word w2) {

        System.out.println("In xor(w1, w2)"); //TODO: delete line
        int[] resultBytes = new int[4];

        for (int i = 0; i < 4; i++) {
            resultBytes[i] = (w1.get(i).getByte() ^ w2.get(i).getByte());
        }

        return new Word(resultBytes);
    }


    public State getState() {
        return keyState;
    }


    public void switchToNextKey() {

        System.out.println("In switchToNextKey"); //TODO: delete line
        Word[] words = keyState.getWordVector();
        Word lastWord = new Word(words[3].getBytes());

        rotateWord(lastWord);
        substituteWord(lastWord);
        // TODO: please write something more readable instead, for god's sake:
        lastWord.set(0, new HexByte((lastWord.get(0).getByte() ^ rcon[roundCount++])));

        words[0] = xor(lastWord, words[0]);
        words[1] = xor(words[0], words[1]);
        words[2] = xor(words[1], words[2]);
        words[3] = xor(words[2], words[3]);

        keyState.updateState(State.Changed.WORD_VECTOR);

        if (roundCount == 9) {
            roundCount = 0;
        }
    }
}
