package com.hayanesh.codec.huffman;

import com.hayanesh.codec.Codec;
import com.igormaznitsa.jbbp.utils.JBBPIntCounter;
import com.igormaznitsa.jbbp.utils.JBBPUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HuffmanCodec implements Codec {

    @Override
    public byte[] encode(String input) {
        var characterFrequencyMap = buildFrequencyMap(input);
        var huffmanTree = HuffmanTree.fromCharacterFrequencyMap(characterFrequencyMap);

        log.info("Average code length : " + huffmanTree.averageCodeLength());
        return combine(huffmanTree.toHuffString(), convertTextToHuffCoding(huffmanTree, input));
    }

    public Map<Character, Integer> buildFrequencyMap(String input){
        Map<Character, Integer> characterFrequencyMap = new HashMap<>();
        for(char letter : input.toCharArray()){
            characterFrequencyMap.compute(letter, (key, value) -> value == null ? 1 : ++value);
        }
        return Collections.unmodifiableMap(characterFrequencyMap);
    }

    @Override
    public String decode(byte[] input) {
        var indexPointer = new JBBPIntCounter();

        String huffmanTreeText = extractHuffmanTreeText(input, indexPointer);
        String huffCoding = extractHuffCoding(input, indexPointer);

        HuffmanTree huffmanTree = HuffmanTree.fromHuffString(huffmanTreeText);
        return convertHuffCodingToText(huffmanTree, huffCoding);
    }

    private String convertTextToHuffCoding(HuffmanTree huffmanTree, String input){
        StringBuilder builder = new StringBuilder();
        input.codePoints()
                .mapToObj(i -> huffmanTree.getHuffCode((char) i))
                .forEach(builder::append);

        return builder.toString();
    }

    private String convertHuffCodingToText(HuffmanTree huffmanTree, String huffCoding){
        StringBuilder builder = new StringBuilder();
        var root = huffmanTree.getRoot();
        var current = root;

        for(char bit : huffCoding.toCharArray()){
            current = bit == '0' ? current.getLeft() : current.getRight();
            if(current == null){
                current = root;
            }
            else if(current.isLeaf()){
                builder.append(current.getLetter());
                current = root;
            }
        }
        return builder.toString();
    }

    private static String extractHuffmanTreeText(byte[] input, JBBPIntCounter counter) {
        int keySize = JBBPUtils.unpackInt(input, counter);
        int keyEndPosition = counter.get() + keySize;
        String key = new String(Arrays.copyOfRange(input, counter.get(), keyEndPosition));
        counter.set(keyEndPosition);
        return key;
    }

    private static String extractHuffCoding(byte[] input, JBBPIntCounter counter) {
        return JBBPUtils.bin2str(Arrays.copyOfRange(input, counter.get(), input.length));
    }

    private static byte[] combine(String key, String binaryString){
        byte[] keyAsBytes = key.getBytes();

        return JBBPUtils.concat(
                JBBPUtils.packInt(keyAsBytes.length),
                keyAsBytes,
                JBBPUtils.str2bin(binaryString)
        );
    }


}
