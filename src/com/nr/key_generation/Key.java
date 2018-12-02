package com.nr.key_generation;


public class Key {

    private byte[] keyBytes;

    Key(byte[] keyBytes) {

        this.keyBytes = keyBytes;
    }

    public byte[] getKeyBytes() {
        return keyBytes;
    }

    public void switchToNextKey() {

    }
}
