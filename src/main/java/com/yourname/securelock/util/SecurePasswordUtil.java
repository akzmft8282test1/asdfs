package com.yourname.securelock.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class SecurePasswordUtil {
    private static final int ITERATIONS = 1000;

    public static String generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hashPassword(String password, String salt) {
        try {
            byte[] result = (password + salt).getBytes(StandardCharsets.UTF_8);

            // SHA-512 반복 적용
            MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
            for (int i = 0; i < ITERATIONS; i++) {
                result = sha512.digest(result);
            }

            // SHA-256로 최종 해싱
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            result = sha256.digest(result);

            return Base64.getEncoder().encodeToString(result);
        } catch (Exception e) {
            throw new RuntimeException("비밀번호 해시 실패", e);
        }
    }

    public static boolean validatePassword(String inputPassword, String salt, String expectedHash) {
        String inputHash = hashPassword(inputPassword, salt);
        return expectedHash.equals(inputHash);
    }
}
