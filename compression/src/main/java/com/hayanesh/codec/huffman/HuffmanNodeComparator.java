package com.hayanesh.codec.huffman;

import java.util.Comparator;

class HuffmanNodeComparator implements Comparator<HuffmanNode> {
    @Override
    public int compare(HuffmanNode o1, HuffmanNode o2) {
        return o1.getWeight() - o2.getWeight();
    }
}
