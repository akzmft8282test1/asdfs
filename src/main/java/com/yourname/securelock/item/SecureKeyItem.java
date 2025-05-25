package com.example.securelock.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class SecureKeyItem extends Item {
    public SecureKeyItem(Settings settings) {
        super(settings);
    }

    public static void bindToLock(ItemStack stack, String lockId, UUID ownerUuid) {
        NbtCompound tag = stack.getOrCreateNbt();
        tag.putString("LockID", lockId);
        tag.putUuid("OwnerUUID", ownerUuid);
    }

    public static boolean isValidFor(String lockId, UUID ownerUuid, ItemStack stack) {
        if (!stack.hasNbt()) return false;
        NbtCompound tag = stack.getNbt();
        return tag != null
                && lockId.equals(tag.getString("LockID"))
                && ownerUuid.equals(tag.getUuid("OwnerUUID"));
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        if (stack.hasNbt()) {
            NbtCompound tag = stack.getNbt();
            tooltip.add(Text.literal("🔐 열쇠 ID: " + tag.getString("LockID")).formatted(Formatting.GRAY));
            tooltip.add(Text.literal("👤 소유자: " + tag.getUuid("OwnerUUID")).formatted(Formatting.GRAY));
        } else {
            tooltip.add(Text.literal("🛠 미등록 열쇠").formatted(Formatting.RED));
        }
    }
}
