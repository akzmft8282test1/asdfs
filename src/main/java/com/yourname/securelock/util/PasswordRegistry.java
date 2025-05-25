package com.yourname.securelock.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraft.server.MinecraftServer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class PasswordRegistry {
    private static final Map<String, PasswordData> passwordMap = new HashMap<>();
    private static final Gson gson = new Gson();
    private static File saveFile;

    public static void init(MinecraftServer server) {
        File dir = new File(server.getRunDirectory(), "securelock_data");
        if (!dir.exists()) dir.mkdirs();
        saveFile = new File(dir, "passwords.json");

        load();
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(saveFile)) {
            gson.toJson(passwordMap, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        if (!saveFile.exists()) return;
        try (FileReader reader = new FileReader(saveFile)) {
            Type type = new TypeToken<Map<String, PasswordData>>() {}.getType();
            Map<String, PasswordData> loaded = gson.fromJson(reader, type);
            passwordMap.clear();
            passwordMap.putAll(loaded);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void registerPassword(String id, String password) {
        String salt = SecurePasswordUtil.generateSalt();
        String hash = SecurePasswordUtil.hashPassword(password, salt);
        passwordMap.put(id, new PasswordData(hash, salt));
        save();
    }

    public static boolean validate(String id, String input) {
        PasswordData data = passwordMap.get(id);
        if (data == null) return false;
        return SecurePasswordUtil.validatePassword(input, data.salt(), data.hash());
    }

    public static PasswordData getPasswordData(String id) {
        return passwordMap.get(id);
    }

    public record PasswordData(String hash, String salt) {}
}
