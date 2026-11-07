package com.example.aes.cdb.nopad.encrypt;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class Pkcs5NopadDecryptor {
    private static final String TRANSFORMATION = "AES/CBC/NoPadding";
    private static final Path CIPHERTEXT_FILE = Path.of("ciphertext.txt");

    public static void main(String[] args) throws Exception {
        decryptAndPrint();
    }

    public static void decryptAndPrint() throws Exception {
        if (!Pkcs5NopadKeyFileGenerator.keyFileExists()) {
            System.out.println("鍵ファイルが見つかりません。先に KeyFileGenerator を実行してください。");
            return;
        }

        SecretKey key = Pkcs5NopadKeyFileGenerator.loadKey();

        List<String> lines = Files.readAllLines(CIPHERTEXT_FILE);
        byte[] iv = Base64.getDecoder().decode(lines.get(0));
        String encryptedBase64 = lines.get(1);
        byte[] encrypted = Base64.getDecoder().decode(encryptedBase64);

        String decrypted = decrypt(encrypted, key, iv);

        System.out.println("encrypted  : " + encryptedBase64);
        System.out.println("decrypted  : " + decrypted);
    }

    private static String decrypt(byte[] encrypted, SecretKey key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        return new String(cipher.doFinal(encrypted), StandardCharsets.UTF_8);
    }
}
