package com.example.aes.cdb.pkcs5pad;

import com.example.aes.cdb.pkcs5pad.encrypt.Pkcs5Encryptor;
import com.example.aes.cdb.pkcs5pad.encrypt.Pkcs5KeyFileGenerator;

public class Main {
    public static void main(String[] args) throws Exception {
        Pkcs5KeyFileGenerator.generateKey();
        Pkcs5Encryptor.encryptAndSave("1");
        Pkcs5Encryptor.encryptAndSave("12");
        Pkcs5Encryptor.encryptAndSave("123");
        Pkcs5Encryptor.encryptAndSave("1234");
        Pkcs5Encryptor.encryptAndSave("12345");
        Pkcs5Encryptor.encryptAndSave("123456");
        Pkcs5Encryptor.encryptAndSave("1234567");
        Pkcs5Encryptor.encryptAndSave("12345678");
        Pkcs5Encryptor.encryptAndSave("1234567890123456");
        Pkcs5Encryptor.encryptAndSave("12345678901234561234567890123456");
    }
}
