package com.yourname.securelock.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class KeyEncoderBlock extends Block {
    public KeyEncoderBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state, BlockView world) {
        return new KeyEncoderBlockEntity(pos, state);
    }

    @Override
    public boolean hasBlockEntity(BlockState state) {
        return true;
    }
}
