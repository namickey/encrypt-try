package com.example;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class Main {
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 128;

    public static void main(String[] args) throws Exception {
        String plainText = "hello, encrypt-try!";

        SecretKey key = KeyGenerator.getInstance("AES").generateKey();

        byte[] iv = new byte[GCM_IV_LENGTH];
        new SecureRandom().nextBytes(iv);

        byte[] encrypted = encrypt(plainText.getBytes(StandardCharsets.UTF_8), key, iv);
        String decrypted = decrypt(encrypted, key, iv);

        System.out.println("plain text : " + plainText);
        System.out.println("encrypted  : " + Base64.getEncoder().encodeToString(encrypted));
        System.out.println("decrypted  : " + decrypted);
    }

    private static byte[] encrypt(byte[] plain, SecretKey key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(GCM_TAG_LENGTH, iv));
        return cipher.doFinal(plain);
    }

    private static String decrypt(byte[] encrypted, SecretKey key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(GCM_TAG_LENGTH, iv));
        return new String(cipher.doFinal(encrypted), StandardCharsets.UTF_8);
    }
}
