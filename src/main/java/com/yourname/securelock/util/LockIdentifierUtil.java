package com.yourname.securelock.util;

import java.util.UUID;

public class LockIdentifierUtil {
    public static String generateLockId() {
        return UUID.randomUUID().toString();
    }
}
