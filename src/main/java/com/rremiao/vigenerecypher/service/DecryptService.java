package com.rremiao.vigenerecypher.service;

import org.springframework.stereotype.Service;

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
}
