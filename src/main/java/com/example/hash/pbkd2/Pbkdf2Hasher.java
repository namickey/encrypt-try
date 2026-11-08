package com.example.hash.pbkd2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Pbkdf2Hasher {
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    // OWASP Password Storage Cheat Sheet(2023年版)の推奨値:
    // PBKDF2-HMAC-SHA256は最低600,000回のイテレーションが推奨されている。
    private static final int ITERATIONS = 600_000;
    private static final int KEY_LENGTH_BITS = 256;
    // ソルトは128ビット(16バイト)以上が推奨(NIST SP 800-132)。
    private static final int SALT_LENGTH = 16;
    private static final Path PASSWORD_FILE = Path.of("password.txt");

    public static void main(String[] args) throws Exception {
        hashAndSave("correct-horse-battery-staple");
    }

    public static void hashAndSave(String password) throws Exception {
        // ソルトはハッシュ化のたびに新しく生成する(使い回さない)。
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);

        long start = System.currentTimeMillis();
        byte[] hash = hash(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH_BITS);
        long elapsedMillis = System.currentTimeMillis() - start;

        String saltBase64 = Base64.getEncoder().encodeToString(salt);
        String hashBase64 = Base64.getEncoder().encodeToString(hash);

        // 検証時に同じ条件で再計算できるよう、イテレーション回数・ソルト・ハッシュ値をセットで保存する。
        String line = ITERATIONS + ":" + saltBase64 + ":" + hashBase64;
        Files.writeString(PASSWORD_FILE, line);

        System.out.println("salt : " + saltBase64);
        System.out.println("hash : " + hashBase64);
        System.out.println("ハッシュ化にかかった時間 : " + elapsedMillis + "ミリ秒");
        System.out.println("イテレーション回数・ソルト・ハッシュ値をbase64形式で " + PASSWORD_FILE + " に保存しました");
    }

    static byte[] hash(char[] password, byte[] salt, int iterations, int keyLengthBits) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLengthBits);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            return factory.generateSecret(spec).getEncoded();
        } finally {
            spec.clearPassword();
        }
    }
}
