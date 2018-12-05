package com.nr.key_generation;


import com.nr.helpers.State;

public class Key {

    private byte[] keyBytes;
    private State keyState;

    Key(byte[] keyBytes) {
        this.keyBytes = keyBytes;
        keyState = new State(keyBytes);
    }

    public byte[] getKeyBytes() {
        return keyBytes;
    }

    public State getState() {
        return keyState;
    }

    public void switchToNextKey() {

    }
}
