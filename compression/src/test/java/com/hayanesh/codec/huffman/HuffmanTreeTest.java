package com.hayanesh.codec.huffman;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HuffmanTreeTest {

    @ParameterizedTest(name = "Check if {argumentsWithNames} has correct huffman code")
    @CsvSource(value = {"Z:111100", "M:11111", "K:111101"}, delimiter = ':')
    void givenCharacterFrequencyMapThenReturnItsHuffmanCode(Character letter, String expectedCode){
        HuffmanTree huffmanTree = HuffmanTree.fromCharacterFrequencyMap(Map.of(
                'C', 32,
                'D', 42,
                'E', 120,
                'K', 7,
                'L', 42,
                'M', 24,
                'U', 37,
                'Z', 2
        ));
        String actualCode = huffmanTree.getHuffCode(letter);
        assertEquals(expectedCode, actualCode);
    }

    @Test
    void givenCharacterFrequencyMapThenReturnIsHuffmanPreorderString(){
        HuffmanTree huffmanTree = HuffmanTree.fromCharacterFrequencyMap(Map.of(
                'C', 32,
                'D', 42,
                'E', 120,
                'K', 7,
                'L', 42,
                'M', 24,
                'U', 37,
                'Z', 2
        ));
        String actualCode = huffmanTree.toHuffString();
        assertEquals("㑖E⍅⍅㑖㑖U⍅⍅D⍅⍅㑖L⍅⍅㑖C⍅⍅㑖㑖Z⍅⍅K⍅⍅M⍅⍅", actualCode);
    }

    @Test
    void givenHuffPreOrderStringThenReturnIsHuffmanTree(){
        String expectedCode = "㑖E⍅⍅㑖㑖U⍅⍅L⍅⍅㑖D⍅⍅㑖C⍅⍅㑖㑖Z⍅⍅K⍅⍅M⍅⍅";
        HuffmanTree huffmanTree = HuffmanTree.fromHuffString(expectedCode);
        String actualCode = huffmanTree.toHuffString();
        assertEquals(expectedCode, actualCode);
    }

}