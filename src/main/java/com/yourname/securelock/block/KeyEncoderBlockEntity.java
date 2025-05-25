package com.example.securelock.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class KeyEncoderBlockEntity extends BlockEntity {
    public KeyEncoderBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.KEY_ENCODER, pos, state);
    }

    // 필요한 정보 저장 가능 (예: 등록된 자물쇠 ID)
}
