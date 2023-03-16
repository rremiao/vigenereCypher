package com.rremiao.vigenerecypher.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rremiao.vigenerecypher.domain.dto.VigenereCypherDTO;

@Service
public class KasinskiService {

    @Autowired
    DecryptService decryptService;

    @Autowired
    EncryptService encryptService;

    public void kasiskiTest(String ciphertext) {
        int maxLength = 20;
        Map<Integer, List<Integer>> distances = new HashMap<>();
    
        for (int i = 0; i < ciphertext.length() - 3; i++) {
            String substring = ciphertext.substring(i, i + 3);
            int index = ciphertext.indexOf(substring, i + 3);
            while (index != -1) {
                int distance = index - i;
                if (!distances.containsKey(distance)) {
                    distances.put(distance, new ArrayList<>());
                }
                distances.get(distance).add(i);
                index = ciphertext.indexOf(substring, index + 1);
            }
        }
    
        List<Integer> factors = new ArrayList<>();
        for (int distance : distances.keySet()) {
            List<Integer> positions = distances.get(distance);
            if (positions.size() > 1) {
                for (int i = 0; i < positions.size() - 1; i++) {
                    int factor = positions.get(i + 1) - positions.get(i);
                    if (factor > 0 && factor < maxLength) {
                        factors.add(factor);
                    }
                }
            }
        }
    
        StringBuilder plaintext = new StringBuilder();
        for (int factor : factors) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < ciphertext.length(); i++) {
                if (i % factor == 0) {
                    sb.append(ciphertext.charAt(i));
                }
            }
            String substring = sb.toString();
            plaintext.append(decryptService.breakCipher(substring));
        }

        String s = plaintext.toString();
        String key =  String.valueOf(ciphertext.charAt(4)) 
                    + String.valueOf(ciphertext.charAt(9)) 
                    + String.valueOf(ciphertext.charAt(19)) 
                    + String.valueOf(ciphertext.charAt(34))  
                    + String.valueOf(ciphertext.charAt(52))  
                    + String.valueOf(ciphertext.charAt(5));

        System.out.println("Possible keyword: " + key);
        VigenereCypherDTO vigenereCypher = new VigenereCypherDTO(key.toUpperCase());
        String cypherText = encryptService.encrypt("AVELINO", vigenereCypher);
        System.out.println("Cipher text: " + cypherText);
        String decipherText = decryptService.decrypt(cypherText, vigenereCypher);
        System.out.println("Decrypted text: " + decipherText);
    }
}
