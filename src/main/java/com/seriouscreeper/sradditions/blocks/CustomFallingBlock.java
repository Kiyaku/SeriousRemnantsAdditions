package com.seriouscreeper.sradditions.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class CustomFallingBlock extends EntityFallingBlock {
    public CustomFallingBlock(World worldIn, BlockPos pos, IBlockState blockState) {
        super(worldIn, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, blockState);
    }


    @Override
    public void onUpdate() {
        if (this.fallTime > 20 && Math.abs(this.motionX) + Math.abs(this.motionY) + Math.abs(this.motionZ) < 0.1) {
            setDead();
        }

        super.onUpdate();
    }


    @Override
    public void setDead() {
        if (!this.isDead){
            this.isDead = true;
        }
    }
}
