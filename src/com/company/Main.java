package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    static final String INPUT_CIPHER_PATH = new File("./src/com/company/inputCipher.txt").getPath();
    static final String INPUT_STRING_PATH = new File("./src/com/company/inputString.txt").getPath();
    static final String OUTPUT_CIPHER_PATH = new File("./src/com/company/outputCipher.txt").getPath();
    static final String OUTPUT_DECRYPTION_PATH = new File("./src/com/company/outputDecryption.txt").getPath();

    static final char[] alphabet = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G',
                                                'H', 'I', 'J', 'K', 'L', 'M', 'N',
                                                    'O', 'P', 'Q', 'R', 'S', 'T', 'U',
                                                        'V', 'W', 'X', 'Y', 'Z', '_'};

    public static void main(String[] args) throws IOException {
        System.out.println("Enter your text (in the English alphabet + '_') to be encypted:");
        String input = inputWithValidation();
        System.out.println("Enter your encryption key (in the English alphabet + '_'):");
        String key = inputWithValidation();
        System.out.println("Encrypted text: " + encryption(input, key));

        System.out.println("Enter your encrypted text (in the English alphabet + '_'):");
        String cipher = inputWithValidation();
        System.out.println("Enter your encryption key (in the English alphabet + '_'):");
        key = inputWithValidation();
        System.out.println("Decrypted text: " + decryption(cipher,key));

        String inputFromFile = readTextFromFile(INPUT_STRING_PATH);
        String keyFromFile = readKeyFromFile(INPUT_STRING_PATH);
        String cipherToFile = encryption(inputFromFile, keyFromFile);
        writeResultToFile(OUTPUT_CIPHER_PATH, cipherToFile);

        inputFromFile = readTextFromFile(INPUT_CIPHER_PATH);
        keyFromFile = readKeyFromFile(INPUT_CIPHER_PATH);
        String outputToFile = decryption(inputFromFile, keyFromFile);
        writeResultToFile(OUTPUT_DECRYPTION_PATH,outputToFile);
    }

    static String inputWithValidation() {
        Scanner scanner = new Scanner(System.in);
        int isInAlphabet = 0;
        String text = " ";
        while (isInAlphabet != text.length()) {
            text = scanner.nextLine().toUpperCase().replaceAll("\\s+", "");
            for (char c : text.toCharArray()) {
                for (char a : alphabet) {
                    if (c == a) {
                        isInAlphabet++;
                        break;
                    }
                }
            }
            if (isInAlphabet != text.length()) {
                isInAlphabet = 0;
                System.out.println("The text contains a letter that is not in the alphabet! Try again:");
            }
        }
        return text;
    }

    static String encryption(String t, String k) {
        char[] text = textPreparation(t);
        //Tworzenie klucza równego wielkości tekstu
        char[] key = keyPreparation(k, t.length());


        char[] outputCipher = new char[t.length()];

        for (int i = 0; i < t.length(); i++) {
            int textCharIndexInAlphabet = new String(alphabet).indexOf(text[i]);
            int keyCharIndexInAlphabet = new String(alphabet).indexOf(key[i]);
            int vigenere_Formula = (keyCharIndexInAlphabet + textCharIndexInAlphabet) % 27;
            outputCipher[i] = new String(alphabet).charAt(vigenere_Formula);
        }

        return new String(outputCipher);
    }

    static String decryption(String c, String k) {
        char[] text = textPreparation(c);
        //Tworzenie klucza równego wielkości tekstu
        char[] key = keyPreparation(k, c.length());

        char[] outputText = new char[c.length()];

        for (int i = 0; i < c.length(); i++) {

            int cipherCharIndexInAlphabet = new String(alphabet).indexOf(text[i]);
            int keyCharIndexInAlphabet = new String(alphabet).indexOf(key[i]);

            int decryptionFormula = 0;
            if(cipherCharIndexInAlphabet >= keyCharIndexInAlphabet){
                decryptionFormula = Math.abs(keyCharIndexInAlphabet - cipherCharIndexInAlphabet);
                outputText[i] = new String(alphabet).charAt(decryptionFormula);
            } else {
                decryptionFormula = 27 - Math.abs((cipherCharIndexInAlphabet - keyCharIndexInAlphabet)) % 27;
                outputText[i] = new String(alphabet).charAt(decryptionFormula);
            }
        }
        return new String(outputText);
    }

    static char[] textPreparation(String t){
        int textLenght = t.length();
        char[] text = new char[textLenght];
        for (int i = 0; i < textLenght; i++) {
            text[i] = t.charAt(i);
        }
        return text;
    }

    static char[] keyPreparation(String k, int textLength){
        int keyLenght = k.length();
        char[] key = new char[textLength];
        if (keyLenght < textLength) {
            for (int i = 0; i < textLength; i++){
                key[i] = k.charAt(i%keyLenght);
            }
        } else {
            for (int i = 0; i < textLength; i++) {
                key[i] = k.charAt(i);
            }
        }
        return key;
    }

    static String readTextFromFile(String fileName) throws IOException {
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        return bufferedReader.readLine();
    }

    static String readKeyFromFile(String fileName) throws IOException {
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        bufferedReader.readLine();
        return bufferedReader.readLine();
    }

    static void writeResultToFile(String fileName, String result){
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(result);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

