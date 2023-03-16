package com.rremiao.vigenerecypher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rremiao.vigenerecypher.domain.dto.VigenereCypherDTO;

@Service
public class TriggerService {

    @Autowired
    EncryptService encryptService;

    @Autowired
    DecryptService decryptService;

    @Autowired
    KasinskiService kasinskiService;

    public void baseApplication() {
        String key = "LEMON";
        VigenereCypherDTO vigenereCypher = new VigenereCypherDTO(key);
        String plaintext = "ATTACKATDAWN";

        String ciphertext = encryptService.encrypt(plaintext, vigenereCypher);
        String decrypted = decryptService.decrypt(ciphertext, vigenereCypher);

        System.out.println("Plaintext: " + plaintext);
        System.out.println("Ciphertext: " + ciphertext);
        System.out.println("Decrypted text: " + decrypted);
    }
}
