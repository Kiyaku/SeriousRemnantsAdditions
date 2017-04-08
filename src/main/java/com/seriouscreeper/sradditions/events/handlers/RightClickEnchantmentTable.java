package com.seriouscreeper.sradditions.events.handlers;

import net.minecraft.init.Blocks;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class RightClickEnchantmentTable {
    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();

        if(world.getBlockState(event.getPos()).getBlock() == Blocks.ENCHANTING_TABLE) {
            event.getEntityPlayer().sendStatusMessage(new TextComponentTranslation("sradditions.enchanting_table"), true);
            event.setCanceled(true);
        }
    }
}
