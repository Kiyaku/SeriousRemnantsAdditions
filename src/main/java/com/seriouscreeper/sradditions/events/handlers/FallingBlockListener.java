package com.seriouscreeper.sradditions.events.handlers;

import com.seriouscreeper.sradditions.SRAdditions;
import com.seriouscreeper.sradditions.blocks.CustomFallingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.List;


public class FallingBlockListener {
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        World world = event.getWorld();
        IBlockState state = world.getBlockState(event.getPos());
        Block block = state.getBlock();

        if (SRAdditions.fallingBlocks.containsKey(block)) {
            dropBlock(event.getPos(), world, false);
        }
    }


    @SubscribeEvent
    public void onBlockExplosion(ExplosionEvent.Detonate event) {
        List<BlockPos> affectedBlocks = event.getAffectedBlocks();

        World world = event.getWorld();

        for(int i = 0; i < affectedBlocks.size(); i++) {
            dropBlock(affectedBlocks.get(i), world, false);
        }
    }


    private void dropBlock(BlockPos originalPos, World world, boolean checkedNeighbours) {
        for(int i = 0; i < EnumFacing.VALUES.length; i++) {
            EnumFacing dir = EnumFacing.VALUES[i];
            BlockPos pos = originalPos.offset(dir);
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();

            if (SRAdditions.fallingBlocks.containsKey(block)) {
                float chance = SRAdditions.fallingBlocks.get(block);

                if(checkedNeighbours) {
                    chance *= 0.5F;
                }

                if(Math.random() <= chance) {
                    if (block == Blocks.STONE) {
                        world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
                    }

                    if (block == Blocks.GRASS) {
                        world.setBlockState(pos, Blocks.DIRT.getDefaultState());
                    }

                    if (world.isAirBlock(pos.offset(EnumFacing.DOWN))) {
                        CustomFallingBlock fallingBlock = new CustomFallingBlock(world, pos, world.getBlockState(pos));
                        fallingBlock.setHurtEntities(true);

                        if (!world.isRemote)
                            world.spawnEntity(fallingBlock);
                    }

                    if (!checkedNeighbours) {
                        dropBlock(pos, world, true);
                    }
                }
            }
        }
    }
}
