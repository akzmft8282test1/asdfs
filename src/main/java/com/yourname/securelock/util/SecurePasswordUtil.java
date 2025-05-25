package com.yourname.securelock.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class SecurePasswordUtil {
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

            byte[] hash = (password + salt).getBytes(StandardCharsets.UTF_8);
            for (int i = 0; i < 5; i++) { // 반복 강화
                hash = sha512.digest(hash);
                hash = sha256.digest(hash);
            }

            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Hash error", e);
        }
    }

    public static boolean verify(String password, String salt, String expectedHash) {
        return hashPassword(password, salt).equals(expectedHash);
    }
}
