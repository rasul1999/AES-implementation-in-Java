package com.nr.key_generation;


import com.nr.helpers.*;


public class Key {

    private State keyState;
    private int roundCount;
    private OperationType operationType;
    private static final int[] rcon = new int[] {
            0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1B, 0x36
    };

    public Key(int[] keyBytes, OperationType operationType) {

        this.operationType = operationType;
        keyState = new State(keyBytes);
        roundCount = 0;
    }

    private void rotateWord(Word w) {

        HexByte firstByte = new HexByte(w.get(0).getByte());

        for (int i = 0; i < 3; i++) {
            w.set(i, w.get(i + 1));
        }
        w.set(3, firstByte);
    }

    private void substituteWord(Word w) {

        int[][] sBoxMatrix = SBox.getSBox(operationType).getSBoxMatrix();

        for (int i = 0; i < 4; i++) {
            int row = w.get(i).row;
            int col = w.get(i).column;
            w.set(i, new HexByte(sBoxMatrix[row][col]));
        }
    }

    private Word xor(Word w1, Word w2) {

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

        if (roundCount == 10) {

            roundCount = 0;
        }
    }
}
