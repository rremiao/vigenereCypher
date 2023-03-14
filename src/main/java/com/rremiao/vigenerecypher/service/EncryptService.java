package com.rremiao.vigenerecypher.service;

import org.springframework.stereotype.Service;

import com.rremiao.vigenerecypher.domain.dto.VigenereCypherDTO;

@Service
public class EncryptService {

    public String encrypt(String plainText, VigenereCypherDTO vigenereCypher) {
        String cipherText = "";
        int pos = 0;

        for (int i = 0; i < plainText.length(); i++) {
            char charText = plainText.charAt(i);

            if (charText >= 'A' && charText <= 'Z') {
                int moves = vigenereCypher.getKey().charAt(pos) - 'A';
                charText = (char) ((charText + moves - 'A') % 26 + 'A');
                pos = (pos + 1) % vigenereCypher.getKey().length();
            }
            
            cipherText += charText;
        }
        return cipherText;
    }
    
}
