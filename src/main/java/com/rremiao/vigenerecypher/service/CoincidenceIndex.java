package com.rremiao.vigenerecypher.service;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CoincidenceIndex {

    @Autowired
    DecryptService decryptService;

    @Autowired
    EncryptService encryptService;

    private static final int ALPHABET_SIZE = 26;
    private static final double[] LETTER_FREQUENCIES = {
            0.1463, 0.0104, 0.0388, 0.0499, 0.1257, 0.0102, 0.013, 0.0128, 0.0618, 0.004, 0.0002,
            0.0278, 0.0474, 0.0505, 0.1073, 0.0252, 0.012, 0.0653, 0.0781, 0.0434, 0.0463, 0.0167,
            0.0001, 0.0021, 0.0001, 0.0047
    };

    public String decrypt(String ciphertext) {
        int keyLength = findKeyLength(ciphertext);
        String key = findKey(ciphertext, keyLength);
        System.out.println("Key:" + key);
        return DecryptService.decryptText(ciphertext, key);
    }

    private static int findKeyLength(String ciphertext) {
        double[] ioc = new double[ciphertext.length() - 1];
        for (int k = 1; k < Math.min(20, ciphertext.length()); k++) {
            double sum = 0;
            for (int i = 0; i < k; i++) {
                int[] freqs = countLetterFrequencies(getSubstring(ciphertext, i, k));
                sum += indexOfCoincidence(freqs);
            }
            ioc[k - 1] = sum / k;
        }
        return indexOfMaxValue(ioc) + 1;
    }

    private static String findKey(String ciphertext, int keyLength) {
        StringBuilder keyBuilder = new StringBuilder();
        for (int i = 0; i < keyLength; i++) {
            String substring = getSubstring(ciphertext, i, keyLength);
            int[] freqs = countLetterFrequencies(substring);
            int shift = getBestShift(freqs);
            keyBuilder.append((char) (shift + 'a'));
        }
        return keyBuilder.toString();
    }

    private static int[] countLetterFrequencies(String text) {
        int[] freqs = new int[ALPHABET_SIZE];
        for (char c : text.toLowerCase().toCharArray()) {
            if (Character.isLetter(c)) {
                freqs[c - 'a']++;
            }
        }
        return freqs;
    }

    private static double indexOfCoincidence(int[] freqs) {
        double sum = 0;
        double n = 0;
        for (int freq : freqs) {
            sum += freq * (freq - 1);
            n += freq;
        }
        return sum / (n * (n - 1));
    }

    private static int getBestShift(int[] freqs) {
        double[] letterFrequencies = normalizeFrequencies(freqs);
        double[] shiftedFrequencies = new double[ALPHABET_SIZE];
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            for (int j = 0; j < ALPHABET_SIZE; j++) {
                shiftedFrequencies[i] += letterFrequencies[(j + i) % ALPHABET_SIZE] * LETTER_FREQUENCIES[j];
            }
        }
        return indexOfMaxValue(shiftedFrequencies);
    }

    private static double[] normalizeFrequencies(int[] freqs) {
        double[] normalizedFreqs = new double[ALPHABET_SIZE];
        int total = Arrays.stream(freqs).sum();
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            normalizedFreqs[i] = (double) freqs[i] / total;
        }
        return normalizedFreqs;
    }

    private static int indexOfMaxValue(double[] array) {
        int maxIndex = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private static String getSubstring(String text, int start, int step) {
		StringBuilder sb = new StringBuilder();
		for (int i = start; i < text.length(); i += step) {
			sb.append(text.charAt(i));
		}
        return sb.toString();
    }
}
