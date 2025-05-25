package com.yourname.securelock.registry;

import com.yourname.securelock.SecureLockMod;
import com.yourname.securelock.item.KeyItem;
import com.yourname.securelock.item.LockItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {
    public static final Item LOCK = new LockItem(new FabricItemSettings().maxCount(1));
    public static final Item KEY = new KeyItem(new FabricItemSettings().maxCount(1));

    public static void register() {
        Registry.register(Registry.ITEM, new Identifier(SecureLockMod.MOD_ID, "lock"), LOCK);
        Registry.register(Registry.ITEM, new Identifier(SecureLockMod.MOD_ID, "key"), KEY);
    }
}
