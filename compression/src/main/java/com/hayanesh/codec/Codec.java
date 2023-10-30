package com.hayanesh.codec;

public interface Codec {
    byte[] encode(String input);
    String decode(byte[] input);
}
