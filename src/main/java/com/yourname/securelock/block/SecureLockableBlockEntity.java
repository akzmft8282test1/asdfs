package com.yourname.securelock.block;

import com.yourname.securelock.key.KeyRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public class SecureLockableBlockEntity extends BlockEntity {

    private String lockId = "";
    private boolean locked = true;

    public SecureLockableBlockEntity(BlockEntityType<?> type, BlockPos pos, net.minecraft.block.BlockState state) {
        super(type, pos, state);
    }

    public void tryUnlock(ItemStack keyStack, ServerPlayerEntity player) {
        if (!locked) {
            player.sendMessage(Text.literal("🔓 이미 열려 있습니다."), false);
            return;
        }

        NbtCompound tag = keyStack.getOrCreateNbt();
        if (!tag.contains("LockId") || !tag.contains("Owner")) {
            player.sendMessage(Text.literal("❌ 유효하지 않은 열쇠입니다."), false);
            return;
        }

        String keyLockId = tag.getString("LockId");
        UUID keyOwner = tag.getUuid("Owner");

        if (lockId.equals(keyLockId)) {
            UUID realOwner = KeyRegistry.getOwner(lockId);
            if (realOwner != null && realOwner.equals(keyOwner)) {
                locked = false;
                player.sendMessage(Text.literal("✅ 잠금 해제되었습니다."), false);
                // 실제 효과 예: 블럭 상태 변경, 레드스톤 출력 등
            } else {
                player.sendMessage(Text.literal("❌ 이 열쇠는 해당 자물쇠를 열 수 없습니다."), false);
            }
        } else {
            player.sendMessage(Text.literal("❌ 열쇠가 이 자물쇠에 맞지 않습니다."), false);
        }
    }

    public void setLockId(String lockId) {
        this.lockId = lockId;
    }

    public String getLockId() {
        return lockId;
    }

    public boolean isLocked() {
        return locked;
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putString("LockId", lockId);
        nbt.putBoolean("Locked", locked);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        lockId = nbt.getString("LockId");
        locked = nbt.getBoolean("Locked");
    }
}
