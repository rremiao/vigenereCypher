package com.rremiao.vigenerecypher.service;

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
}
