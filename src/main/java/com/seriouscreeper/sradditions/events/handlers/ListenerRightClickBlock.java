package com.seriouscreeper.sradditions.events.handlers;

import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickEmpty;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import toughasnails.api.config.SyncedConfig;
import toughasnails.api.item.TANItems;
import toughasnails.item.ItemCanteen;
import toughasnails.item.ItemTANWaterBottle;


// Heavily inspired by darkhax's "Thirsty Bottles"
public class ListenerRightClickBlock {
    @SubscribeEvent
    public void onItemUsed(RightClickBlock event) {
        if(event.getItemStack() != ItemStack.EMPTY) {
            Item heldItem = event.getItemStack().getItem();

            if (heldItem instanceof ItemCanteen && heldItem.getDamage(event.getItemStack()) != 1) {
                BlockPos pos = new BlockPos(event.getHitVec());
                IBlockState state = event.getWorld().getBlockState(pos);
                EntityPlayer player = event.getEntityPlayer();
                Material material = state.getMaterial();

                if(event.getWorld().getBlockState(event.getPos()).getBlock() == Blocks.CAULDRON) {
                    state = event.getWorld().getBlockState(event.getPos());
                    BlockCauldron cauldron = (BlockCauldron)state.getBlock();
                    event.getWorld().playSound(player, player.posX, player.posY, player.posZ, SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                    cauldron.setWaterLevel(event.getWorld(), event.getPos(), state, state.getValue(BlockCauldron.LEVEL) - 1);
                    heldItem.setDamage(event.getItemStack(), 1);
                } else {
                    event.setCanceled(true);
                }
            }
        }
    }


    // Mostly copied from TAN event
    @SubscribeEvent(receiveCanceled = true, priority = EventPriority.HIGHEST)
    public void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack stack = player.getHeldItem(event.getHand());
        World world = player.world;

        if (stack.getItem().equals(Items.GLASS_BOTTLE))
        {
            int originalCount = stack.getCount();
            // Trick onItemRightClick into not adding any water bottles into the player's inventory
            stack.setCount(1);

            ActionResult actionResult = stack.getItem().onItemRightClick(event.getWorld(), player, event.getHand());
            ItemStack resultStack = ((ItemStack)actionResult.getResult());

            // Only substitute water bottles with dirty water bottles
            if (actionResult.getType() == EnumActionResult.SUCCESS && ItemStack.areItemStackTagsEqual(resultStack, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER)))
            {
                // We must restore the original amount of bottles before continuing to prevent the fake empty bottle
                // stack from being replaced

                // A bottle has been consumed, so reduce the original count by one before it is restored
                originalCount--;
                stack.setCount(originalCount); // Restore original amount of bottles

                player.addStat(StatList.getObjectUseStats(stack.getItem()));
                ItemStack bottleStack = new ItemStack(TANItems.water_bottle);

                if (!player.inventory.addItemStackToInventory(bottleStack))
                {
                    player.dropItem(bottleStack, false);
                }

                RayTraceResult raytraceresult = rayTrace(event.getWorld(), event.getEntityPlayer(), true);

                if (raytraceresult != null) {
                    if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
                        BlockPos blockpos = raytraceresult.getBlockPos();

                        if (!event.getWorld().isBlockModifiable(event.getEntityPlayer(), blockpos) || !event.getEntityPlayer().canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, stack)) {
                        } else if (event.getWorld().getBlockState(blockpos).getMaterial() == Material.WATER) {
                            event.getWorld().setBlockToAir(blockpos);
                        }
                    }
                }

                // Prevent onItemRightClick from being fired a second time for bottles right clicked on water
                event.setCanceled(true);
            }
            else
            {
                // Restore original amount of bottles
                stack.setCount(originalCount);
            }
        } else if(stack.getItem() instanceof ItemCanteen && stack.getItem().getDamage(event.getItemStack()) != 1) {
            RayTraceResult raytraceresult = rayTrace(event.getWorld(), event.getEntityPlayer(), true);

            if (raytraceresult != null) {
                if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
                    BlockPos blockpos = raytraceresult.getBlockPos();

                    if (!event.getWorld().isBlockModifiable(event.getEntityPlayer(), blockpos) || !event.getEntityPlayer().canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, stack)) {
                    } else if (event.getWorld().getBlockState(blockpos).getMaterial() == Material.WATER) {
                        event.getWorld().playSound(player, player.posX, player.posY, player.posZ, SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                        event.getWorld().setBlockToAir(blockpos);
                        stack.getItem().setDamage(stack, 1);
                    }
                }
            }
        }
    }


    protected RayTraceResult rayTrace(World worldIn, EntityPlayer playerIn, boolean useLiquids)
    {
        float f = playerIn.rotationPitch;
        float f1 = playerIn.rotationYaw;
        double d0 = playerIn.posX;
        double d1 = playerIn.posY + (double)playerIn.getEyeHeight();
        double d2 = playerIn.posZ;
        Vec3d vec3d = new Vec3d(d0, d1, d2);
        float f2 = MathHelper.cos(-f1 * 0.017453292F - (float)Math.PI);
        float f3 = MathHelper.sin(-f1 * 0.017453292F - (float)Math.PI);
        float f4 = -MathHelper.cos(-f * 0.017453292F);
        float f5 = MathHelper.sin(-f * 0.017453292F);
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d3 = 5.0D;
        if (playerIn instanceof net.minecraft.entity.player.EntityPlayerMP)
        {
            d3 = ((net.minecraft.entity.player.EntityPlayerMP)playerIn).interactionManager.getBlockReachDistance();
        }
        Vec3d vec3d1 = vec3d.addVector((double)f6 * d3, (double)f5 * d3, (double)f7 * d3);
        return worldIn.rayTraceBlocks(vec3d, vec3d1, useLiquids, !useLiquids, false);
    }
}
