package com.rremiao.vigenerecypher.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rremiao.vigenerecypher.domain.dto.VigenereCypherDTO;

@Service
public class DecryptService {
    private static int AlphabetSize = 26;

    public static String decryptText(String ciphertext, String key) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i++) {
            char c = ciphertext.charAt(i);
            if (Character.isLetter(c)) {
                int shift = key.charAt(i % key.length()) - 'a';
                char p = (char) ((c - shift - 'a' + AlphabetSize) % AlphabetSize + 'a');
                sb.append(p);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public List<Integer> breakCipher(String ciphertext) {
        Map<Character, Integer> frequencyMap = new HashMap<>();

        for (char c : ciphertext.toCharArray()) {
            if (!Character.isLetter(c)) {
                continue;
            }
            c = Character.toUpperCase(c);
            if (!frequencyMap.containsKey(c)) {
                frequencyMap.put(c, 0);
            }
            frequencyMap.put(c, frequencyMap.get(c) + 1);
        }

        List<Map.Entry<Character, Integer>> sortedList = new ArrayList<>(frequencyMap.entrySet());
        sortedList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        List<Integer> shifts = new ArrayList<>();
        for (int i = 0; i < sortedList.size(); i++) {
            char c = sortedList.get(i).getKey();
            int shift = (c - 'E' + 26) % 26;
            shifts.add(shift);
        }
        return shifts;
    }

    // public String breakCipher(String ciphertext) {
    //     Map<Character, Integer> frequencyMap = new HashMap<>();
    
    //     for (char c : ciphertext.toCharArray()) {
    //         if (!Character.isLetter(c)) {
    //             continue;
    //         }
    //         c = Character.toLowerCase(c);
    //         if (!frequencyMap.containsKey(c)) {
    //             frequencyMap.put(c, 0);
    //         }
    //         frequencyMap.put(c, frequencyMap.get(c) + 1);
    //     }
    
    //     List<Map.Entry<Character, Integer>> sortedList = new ArrayList<>(frequencyMap.entrySet());
    //     sortedList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
    
    //     Map<Character, Character> mapping = new HashMap<>();
    //     String expectedFrequency = "etaoinshrdlucmfwypvbgkqjxz";
    //     for (int i = 0; i < sortedList.size(); i++) {
    //         char c = sortedList.get(i).getKey();
    //         mapping.put(c, expectedFrequency.charAt(i));
    //     }
    
    //     StringBuilder plaintext = new StringBuilder();
    //     for (char c : ciphertext.toCharArray()) {
    //         if (!Character.isLetter(c)) {
    //             plaintext.append(c);
    //         } else {
    //             c = Character.toLowerCase(c);
    //             plaintext.append(mapping.get(c));
    //         }
    //     }
    //     return plaintext.toString();
    // }
    
}
