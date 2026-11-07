package com.example.aes.cdb.nopad.encrypt;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Pkcs5NopadKeyFileGenerator {
    // IVは暗号化のたびにEncryptor側で新しく生成するため、
    // このクラスでは鍵のみを生成・保管する。
    static final Path KEY_FILE = Path.of("key.txt");

    public static void main(String[] args) throws Exception {
        generateKey();
    }

    public static void generateKey() throws Exception {
        // サイズを指定していないため、JDK21(SunJCE)のデフォルト鍵長である
        // 256ビット(32バイト)のAES鍵が生成される。
        // 128ビット(16バイト)や192ビット(24バイト)にしたい場合は
        // KeyGenerator#init(int) でサイズを明示する必要がある。
        SecretKey key = KeyGenerator.getInstance("AES").generateKey();

        saveKey(key);

        System.out.println("key をbase64形式で " + KEY_FILE + " に保存しました");
    }

    private static void saveKey(SecretKey key) throws Exception {
        String keyBase64 = Base64.getEncoder().encodeToString(key.getEncoded());
        Files.writeString(KEY_FILE, keyBase64);
    }

    static boolean keyFileExists() {
        return Files.exists(KEY_FILE);
    }

    static SecretKey loadKey() throws Exception {
        List<String> lines = Files.readAllLines(KEY_FILE);
        byte[] keyBytes = Base64.getDecoder().decode(lines.get(0));
        return new SecretKeySpec(keyBytes, "AES");
    }
}
