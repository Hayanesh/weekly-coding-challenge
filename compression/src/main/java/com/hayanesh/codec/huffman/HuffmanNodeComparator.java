package com.hayanesh.codec.huffman;

import java.util.Comparator;

class HuffmanNodeComparator implements Comparator<HuffmanNode> {
    @Override
    public int compare(HuffmanNode o1, HuffmanNode o2) {
        int weightDifference = o1.getWeight() - o2.getWeight();
        if(weightDifference == 0 && o1.isLeaf() && o2.isLeaf())
            return o1.getLetter() - o2.getLetter();
        return weightDifference;
    }
}
