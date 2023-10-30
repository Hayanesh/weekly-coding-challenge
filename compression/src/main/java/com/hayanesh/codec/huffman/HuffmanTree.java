package com.hayanesh.codec.huffman;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
class HuffmanTree {
    public static final char NULL_TOKEN          =  '\u2345';
    public static final char INTERNAL_NODE_TOKEN =  '\u3456';

    @Getter
    private final HuffmanNode root;
    private final Map<Character, String> huffmanCodeForCharacter = new HashMap<>();
    private AtomicInteger index;
    private HuffmanTree(PriorityQueue<HuffmanNode> nodes) {
        this.root = this.buildTreeBasedOnNodeWeight(nodes);
        this.generateHuffCode();
    }

    private HuffmanTree(String value){
        this.root = this.buildTreeBasedOnNodeOrder(value);
    }

    public static HuffmanTree fromCharacterFrequencyMap(Map<Character, Integer> characterFrequencyMap){
        var heap = buildMinHeap(characterFrequencyMap);
        return new HuffmanTree(heap);
    }

    public static HuffmanTree fromHuffString(String value){
        return new HuffmanTree(value);
    }

    public String getHuffCode(char letter){
        return huffmanCodeForCharacter.get(letter);
    }

    public String toHuffString(){
        return createPreOrderTraversalString();
    }

    private void generateHuffCode(){
        traverseHuffTree(root, "");
    }

    public long averageCodeLength(){
        long sumOfCodeLength = huffmanCodeForCharacter.values()
                .stream()
                .mapToLong(String::length)
                .sum();

        long numberOfCodes = huffmanCodeForCharacter.size();
        return sumOfCodeLength / numberOfCodes;
    }

    private static PriorityQueue<HuffmanNode> buildMinHeap(Map<Character, Integer> characterFrequencyMap){
        var minHeap = new PriorityQueue<>(new HuffmanNodeComparator());
        for(var entry : characterFrequencyMap.entrySet()){
            minHeap.add(HuffmanNode.newLeafNode(entry.getKey(), entry.getValue()));
        }
        return minHeap;
    }

    private HuffmanNode buildTreeBasedOnNodeWeight(PriorityQueue<HuffmanNode> nodes){
        while (nodes.size() > 1){
            var leastWeightNodeOne = nodes.poll();
            var leastWeightNodeTwo = nodes.poll();

            nodes.add(HuffmanNode.newInternalNode(leastWeightNodeOne, leastWeightNodeTwo));
        }
        return nodes.poll();
    }

    private HuffmanNode buildTreeBasedOnNodeOrder(String key){
        List<Character> letters =  key.codePoints()
                .mapToObj(i -> (char)i)
                .toList();
        index = new AtomicInteger(0);
        return doPreOrderTraversal(letters);
    }

    private HuffmanNode doPreOrderTraversal(List<Character> letters){
        char letter = letters.get(index.get());
        if(letter == NULL_TOKEN)
            return null;

        HuffmanNode node =  HuffmanNode.newNode(letter == INTERNAL_NODE_TOKEN ? null : letter);

        index.incrementAndGet();
        node.setLeft(doPreOrderTraversal(letters));

        index.incrementAndGet();
        node.setRight(doPreOrderTraversal(letters));
        return node;
    }

    private String createPreOrderTraversalString(){
        Stack<HuffmanNode> stack = new Stack<>();
        stack.add(root);

        List<Character> characters = new ArrayList<>();

        while (!stack.isEmpty()){
            var node = stack.pop();

            if(node == null)
                characters.add(NULL_TOKEN);
            else {
                characters.add(node.isLeaf() ? node.getLetter() : INTERNAL_NODE_TOKEN);
                stack.add(node.getRight());
                stack.add(node.getLeft());
            }
        }
        return characters.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    private void traverseHuffTree(HuffmanNode node, String code){
        if(node.isLeaf()){
            huffmanCodeForCharacter.put(node.getLetter(), code);
            return;
        }

        traverseHuffTree(node.getLeft(), code + "0");
        traverseHuffTree(node.getRight(), code + "1");
    }
}
