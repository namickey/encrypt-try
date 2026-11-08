package com.example.hash.pbkd2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.Base64;

public class Pbkdf2Verifier {
    private static final Path PASSWORD_FILE = Path.of("password.txt");

    public static void main(String[] args) throws Exception {
        System.out.println("正しいパスワード  : " + verify("correct-horse-battery-staple"));
        System.out.println("間違ったパスワード: " + verify("wrong-password"));
    }

    public static boolean verify(String password) throws Exception {
        if (!Files.exists(PASSWORD_FILE)) {
            System.out.println("パスワードファイルが見つかりません。先に Pbkdf2Hasher を実行してください。");
            return false;
        }

        String[] parts = Files.readString(PASSWORD_FILE).split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = Base64.getDecoder().decode(parts[1]);
        byte[] expectedHash = Base64.getDecoder().decode(parts[2]);

        byte[] actualHash = Pbkdf2Hasher.hash(password.toCharArray(), salt, iterations, expectedHash.length * 8);

        // タイミング攻撃を防ぐため、バイト列の比較は定数時間で行う。
        return MessageDigest.isEqual(expectedHash, actualHash);
    }
}
