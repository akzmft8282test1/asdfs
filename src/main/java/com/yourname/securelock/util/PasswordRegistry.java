package com.yourname.securelock.util;

import com.google.gson.*;
import net.minecraft.server.MinecraftServer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

public class PasswordRegistry {
    private static final HashMap<String, PasswordEntry> PASSWORDS = new HashMap<>();
    private static File saveFile;

    public static void init(MinecraftServer server) {
        saveFile = new File(server.getSavePath(WorldSavePath.ROOT).toFile(), "securelock/passwords.json");
        load();
    }

    public static void register(String id, String password) {
        String salt = SecurePasswordUtil.generateSalt();
        String hash = SecurePasswordUtil.hashPassword(password, salt);
        PASSWORDS.put(id, new PasswordEntry(hash, salt));
        save();
    }

    public static boolean validate(String id, String input) {
        PasswordEntry entry = PASSWORDS.get(id);
        if (entry == null) return false;
        return SecurePasswordUtil.verify(input, entry.salt, entry.hash);
    }

    private static void save() {
        try (FileWriter writer = new FileWriter(saveFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(PASSWORDS, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void load() {
        if (!saveFile.exists()) return;
        try (FileReader reader = new FileReader(saveFile)) {
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(reader, JsonObject.class);
            json.entrySet().forEach(entry -> {
                JsonObject o = entry.getValue().getAsJsonObject();
                PASSWORDS.put(entry.getKey(), new PasswordEntry(
                    o.get("hash").getAsString(),
                    o.get("salt").getAsString()
                ));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static record PasswordEntry(String hash, String salt) {}
}
