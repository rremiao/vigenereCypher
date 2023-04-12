package com.rremiao.vigenerecypher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rremiao.vigenerecypher.service.CoincidenceIndex;

@SpringBootApplication
public class VigenerecypherApplication implements CommandLineRunner {

	@Autowired
	CoincidenceIndex coincidenceIndexService;

	public static void main(String[] args) {
		SpringApplication.run(VigenerecypherApplication.class, args);
	}

	public String readCipherTextFromFile(String filePath) {
		StringBuilder cipherText = new StringBuilder();

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				cipherText.append(line);
			}
		} catch (IOException e) {
			System.err.println("Erro ao ler o arquivo de texto: " + e.getMessage());
			return null;
		}

		return cipherText.toString();
	}

	@Override
	public void run(String... args) throws Exception {
		File file = new File("cifrado.txt");
		String absolutePath = file.getAbsolutePath();

		String cipherText = readCipherTextFromFile(absolutePath);

		if (cipherText != null) {
			String decryptedText = coincidenceIndexService.decrypt(cipherText);

			FileWriter writer = new FileWriter("decrypted.txt");
			writer.write(decryptedText);
			writer.close();
		}
	}

}
