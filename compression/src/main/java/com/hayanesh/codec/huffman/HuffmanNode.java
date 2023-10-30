package com.hayanesh.codec.huffman;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
class HuffmanNode {
    private final Character letter;
    private final int weight;

    @Setter
    private HuffmanNode left;
    @Setter
    private HuffmanNode right;

    private HuffmanNode(Character letter, int weight) {
        this.letter = letter;
        this.weight = weight;
    }

    public static HuffmanNode newNode(Character letter){
        return new HuffmanNode(letter, 0);
    }

    public static HuffmanNode newLeafNode(Character letter, int weight){
        return new HuffmanNode(letter, weight);
    }

    public static HuffmanNode newInternalNode(HuffmanNode left, HuffmanNode right){
        int weight = 0;

        if(left != null)
            weight += left.weight;

        if(right != null)
            weight += right.weight;

         var node = new HuffmanNode(null, weight);
         node.setLeft(left);
         node.setRight(right);

         return node;
    }

    public boolean isLeaf(){
        return left == null && right == null;
    }

}
