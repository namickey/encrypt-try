package com.example.hash.pbkd2;

public class Main {
    public static void main(String[] args) throws Exception {
        Pbkdf2Hasher.hashAndSave("correct-horse-battery-staple");

        System.out.println("正しいパスワード  : " + Pbkdf2Verifier.verify("correct-horse-battery-staple"));
        System.out.println("間違ったパスワード: " + Pbkdf2Verifier.verify("wrong-password"));
    }
}
