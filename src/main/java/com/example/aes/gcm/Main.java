package com.example.aes.gcm;

import com.example.aes.gcm.encrypt.GcmEncryptor;
import com.example.aes.gcm.encrypt.GcmKeyFileGenerator;

public class Main {
    public static void main(String[] args) throws Exception {
        GcmKeyFileGenerator.generateKey();
        GcmEncryptor.encryptAndSave("1");
        GcmEncryptor.encryptAndSave("12");
        GcmEncryptor.encryptAndSave("123");
        GcmEncryptor.encryptAndSave("1234");
        GcmEncryptor.encryptAndSave("12345");
        GcmEncryptor.encryptAndSave("123456");
        GcmEncryptor.encryptAndSave("1234567");
        GcmEncryptor.encryptAndSave("12345678");
        GcmEncryptor.encryptAndSave("123456789");
        GcmEncryptor.encryptAndSave("1234567890");
    }
}
