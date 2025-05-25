package com.example.securelock;

import com.example.securelock.item.SecureKeyItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class SecureLockMod implements ModInitializer {
    public static final String MOD_ID = "securelock";

    public static final Item SECURE_KEY = new SecureKeyItem(new FabricItemSettings().maxCount(1));

    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "secure_key"), SECURE_KEY);
        System.out.println("[SecureLock] 열쇠 아이템 등록 완료");
    }
}
