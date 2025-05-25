package com.yourname.securelock.key;

import java.util.*;

public class KeyRegistry {
    private static final Map<String, UUID> lockOwners = new HashMap<>();         // 자물쇠 ID → 소유자 UUID
    private static final Map<UUID, Set<String>> ownerLocks = new HashMap<>();    // 소유자 UUID → 자물쇠 ID 목록

    public static void registerLock(String lockId, UUID owner) {
        lockOwners.put(lockId, owner);
        ownerLocks.computeIfAbsent(owner, k -> new HashSet<>()).add(lockId);
    }

    public static UUID getOwner(String lockId) {
        return lockOwners.get(lockId);
    }

    public static Set<String> getLocks(UUID owner) {
        return ownerLocks.getOrDefault(owner, Collections.emptySet());
    }

    public static void updateLockOwner(String lockId, UUID newOwner) {
        UUID oldOwner = lockOwners.get(lockId);
        if (oldOwner != null) {
            ownerLocks.getOrDefault(oldOwner, Collections.emptySet()).remove(lockId);
        }
        registerLock(lockId, newOwner);
    }
}
