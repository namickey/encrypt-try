package com.example.aes.cdb.nopad;

import com.example.aes.cdb.nopad.encrypt.Pkcs5NopadEncryptor;
import com.example.aes.cdb.nopad.encrypt.Pkcs5NopadKeyFileGenerator;

public class Main {
    public static void main(String[] args) throws Exception {
        Pkcs5NopadKeyFileGenerator.generateKey();
        Pkcs5NopadEncryptor.encryptAndSave("1234567890123456");
        Pkcs5NopadEncryptor.encryptAndSave("12345678901234561234567890123456");
    }
}
