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
    public String decrypt(String cipherText, VigenereCypherDTO vigenereCypher) {
        String plainText = "";
        int pos = 0;

        for (int i = 0; i < cipherText.length(); i++) {
            char charText = cipherText.charAt(i);

            if (charText >= 'A' && charText <= 'Z') {
                int moves = vigenereCypher.getKey().charAt(pos) - 'A';
                charText = (char) ((charText - moves - 'A' + 26) % 26 + 'A');
                pos = (pos + 1) % vigenereCypher.getKey().length();
            }

            plainText += charText;
        }
        return plainText;
    }
    public String breakCipher(String ciphertext) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
    
        for (char c : ciphertext.toCharArray()) {
            if (!Character.isLetter(c)) {
                continue;
            }
            c = Character.toLowerCase(c);
            if (!frequencyMap.containsKey(c)) {
                frequencyMap.put(c, 0);
            }
            frequencyMap.put(c, frequencyMap.get(c) + 1);
        }
    
        List<Map.Entry<Character, Integer>> sortedList = new ArrayList<>(frequencyMap.entrySet());
        sortedList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
    
        Map<Character, Character> mapping = new HashMap<>();
        String expectedFrequency = "etaoinshrdlucmfwypvbgkqjxz";
        for (int i = 0; i < sortedList.size(); i++) {
            char c = sortedList.get(i).getKey();
            mapping.put(c, expectedFrequency.charAt(i));
        }
    
        StringBuilder plaintext = new StringBuilder();
        for (char c : ciphertext.toCharArray()) {
            if (!Character.isLetter(c)) {
                plaintext.append(c);
            } else {
                c = Character.toLowerCase(c);
                plaintext.append(mapping.get(c));
            }
        }
        return plaintext.toString();
    }
    
}
