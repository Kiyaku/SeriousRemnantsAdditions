package com.seriouscreeper.sradditions.events.handlers;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


// Heavily inspired by darkhax's "Thirsty Bottles"
public class ListenerRightClickBlock {
    @SubscribeEvent
    public void onItemUsed(RightClickBlock event) {
        if(event.getItemStack() != ItemStack.EMPTY && event.getItemStack().getItem() instanceof ItemGlassBottle) {
            BlockPos pos = new BlockPos(event.getHitVec());
            IBlockState state = event.getWorld().getBlockState(pos);
            EntityPlayer player = event.getEntityPlayer();
            Material material = state.getMaterial();

            if(material == Material.WATER && ((Integer)state.getValue(BlockLiquid.LEVEL)).intValue() == 0) {
                event.getWorld().setBlockToAir(pos);
                event.getWorld().playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                event.getEntityPlayer().setHeldItem(event.getHand(), transformBottle(event.getItemStack(), player, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER)));
            }
        }
    }

    private ItemStack transformBottle(ItemStack input, EntityPlayer player, ItemStack stack) {
        input.setCount(input.getCount() - 1);
        player.addStat(StatList.getObjectUseStats(input.getItem()));

        if (input.getCount() <= 0) {
            return stack;
        }
        else {
            if (!player.inventory.addItemStackToInventory(stack)) {
                player.dropItem(stack, false);
            }

            return input;
        }
    }
}
