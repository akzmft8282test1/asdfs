package com.yourname.securelock.block.entity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LockableBlockEntity extends BlockEntity {
    private String passwordId = "";

    public LockableBlockEntity(BlockEntityType<?> type, BlockPos pos, net.minecraft.block.BlockState state) {
        super(type, pos, state);
    }

    public void setPasswordId(String id) {
        this.passwordId = id;
        markDirty();
    }

    public String getPasswordId() {
        return passwordId;
    }

    public boolean isLocked() {
        return passwordId != null && !passwordId.isEmpty();
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putString("PasswordID", passwordId);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        passwordId = nbt.getString("PasswordID");
    }

    public static void tick(World world, BlockPos pos, net.minecraft.block.BlockState state, LockableBlockEntity be) {
        // 추후 알람/보호막 등 확장용
    }
}
