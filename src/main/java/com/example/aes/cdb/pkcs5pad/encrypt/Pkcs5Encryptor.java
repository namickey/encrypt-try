package com.example.aes.cdb.pkcs5pad.encrypt;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class Pkcs5Encryptor {
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    // CBCのIVはAESのブロックサイズと同じ16バイト固定。
    private static final int CBC_IV_LENGTH = 16;
    private static final Path CIPHERTEXT_FILE = Path.of("ciphertext.txt");

    public static void main(String[] args) throws Exception {
        encryptAndSave("hello, encrypt-try!");
    }

    public static void encryptAndSave(String plainText) throws Exception {
        if (!Pkcs5KeyFileGenerator.keyFileExists()) {
            System.out.println("鍵ファイルが見つかりません。先に KeyFileGenerator を実行してください。");
            return;
        }

        SecretKey key = Pkcs5KeyFileGenerator.loadKey();

        // 同じ鍵でIVを再利用しないよう、暗号化ごとに新しいIVを生成する。
        byte[] iv = new byte[CBC_IV_LENGTH];
        new SecureRandom().nextBytes(iv);

        byte[] encrypted = encrypt(plainText.getBytes(StandardCharsets.UTF_8), key, iv);
        String ivBase64 = Base64.getEncoder().encodeToString(iv);
        String encryptedBase64 = Base64.getEncoder().encodeToString(encrypted);

        // 復号時に同じIVを使えるよう、IVと暗号文をセットで保存する。
        Files.writeString(CIPHERTEXT_FILE, ivBase64 + System.lineSeparator() + encryptedBase64);

        System.out.println("plain text : " + plainText);
        System.out.println("iv         : " + ivBase64);
        System.out.println("encrypted  : " + encryptedBase64);
        System.out.println("iv・暗号文をbase64形式で " + CIPHERTEXT_FILE + " に保存しました");
    }

    private static byte[] encrypt(byte[] plain, SecretKey key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        return cipher.doFinal(plain);
    }
}
