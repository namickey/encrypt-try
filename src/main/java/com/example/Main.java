package com.example;

import com.example.encrypt.Encryptor;
import com.example.encrypt.KeyFileGenerator;

public class Main {
    public static void main(String[] args) throws Exception {
        KeyFileGenerator.generateKey();
        Encryptor.encryptAndSave("1");
        Encryptor.encryptAndSave("12");
        Encryptor.encryptAndSave("123");
        Encryptor.encryptAndSave("1234");
        Encryptor.encryptAndSave("12345");
        Encryptor.encryptAndSave("123456");
        Encryptor.encryptAndSave("1234567");
        Encryptor.encryptAndSave("12345678");
        Encryptor.encryptAndSave("123456789");
        Encryptor.encryptAndSave("1234567890");
    }
}
