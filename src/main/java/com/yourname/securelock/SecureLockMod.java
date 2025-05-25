package com.yourname.securelock;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecureLockMod implements ModInitializer {
    public static final String MOD_ID = "securelock";
    public static final Logger LOGGER = LoggerFactory.getLogger("SecureLock");

    public static final Item LOCK_ITEM = new Item(new FabricItemSettings());
    public static final Item KEY_ITEM = new Item(new FabricItemSettings());

    @Override
    public void onInitialize() {
        LOGGER.info("🔐 SecureLock Mod Initialized!");

        ModItems.register(); //아이템 등록

        // 자물쇠 및 열쇠 아이템 등록
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "lock"), LOCK_ITEM);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "key"), KEY_ITEM);
    }
}
