package com.seriouscreeper.sradditions.events.handlers;

import com.seriouscreeper.sradditions.SRAdditions;
import com.seriouscreeper.sradditions.blocks.CustomFallingBlock;
import com.seriouscreeper.sradditions.config.ConfigHandler;
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
    private BlockPos oriPos;

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        this.oriPos = event.getPos();
        checkNeighbours(event.getWorld(), event.getPos(), false);
    }


    @SubscribeEvent
    public void onBlockExplosion(ExplosionEvent.Detonate event) {
        List<BlockPos> affectedBlocks = event.getAffectedBlocks();

        World world = event.getWorld();

        for(int i = 0; i < affectedBlocks.size(); i++) {
            checkNeighbours(world, affectedBlocks.get(i), false);
        }
    }


    private void checkNeighbours(World world, BlockPos originalPos, boolean checkedNeighbours) {
        Block originalBlock = world.getBlockState(originalPos).getBlock();

        for(int i = 0; i < EnumFacing.VALUES.length; i++) {
            EnumFacing dir = EnumFacing.VALUES[i];
            BlockPos pos = originalPos.offset(dir);
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();

            // if the original block can fracture neighbour blocks, do so
            if(ConfigHandler.EnableBlockFracturing && SRAdditions.fracturingBlocks.containsKey(originalBlock) && Math.random() <= SRAdditions.fracturingBlocks.get(originalBlock)) {
                // fracture
                fractureBlock(world, pos, block, checkedNeighbours);
            }

            // if the new block can be affected by gravity, try to do so
            if(ConfigHandler.EnableBlockGravity && SRAdditions.fallingBlocks.containsKey(block) && Math.random() <= SRAdditions.fallingBlocks.get(block)) {
                // gravity
                dropBlock(world, pos);
            }
        }
    }


    // just make the block fall
    private void dropBlock(World world, BlockPos pos) {
        if(world.isAirBlock(pos.offset(EnumFacing.DOWN)) || pos.offset(EnumFacing.DOWN).equals(this.oriPos)){
            CustomFallingBlock fallingBlock = new CustomFallingBlock(world, pos, world.getBlockState(pos));
            fallingBlock.setHurtEntities(ConfigHandler.GravityBlocksHurt);

            if (!world.isRemote)
                world.spawnEntity(fallingBlock);
        }
    }


    private void fractureBlock(World world, BlockPos pos, Block block, boolean checkedNeighbours) {
        boolean fractured = false;

        // TODO: Let config file determine what blocks break into what?
        if (block == Blocks.STONE) {
            world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
            fractured = true;
        }

        if (block == Blocks.GRASS) {
            world.setBlockState(pos, Blocks.DIRT.getDefaultState());
            fractured = true;
        }

        if(fractured) {
            dropBlock(world, pos);

            if (!checkedNeighbours) {
                checkNeighbours(world, pos, true);
            }
        }
    }
}
