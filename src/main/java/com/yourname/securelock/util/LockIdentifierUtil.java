package com.example.securelock.util;

import java.util.UUID;

public class LockIdentifierUtil {
    public static String generateLockId() {
        return UUID.randomUUID().toString();
    }
}
