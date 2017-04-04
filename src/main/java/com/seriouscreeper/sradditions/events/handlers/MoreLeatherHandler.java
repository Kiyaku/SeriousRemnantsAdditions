package com.seriouscreeper.sradditions.events.handlers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class MoreLeatherHandler {
    @SubscribeEvent
    public void onKilledCows(LivingDeathEvent event) {
        Entity killer = event.getSource().getEntity();

        if(event.getEntity() instanceof EntityCow && killer instanceof EntityLivingBase) {
            if(((EntityLivingBase) killer).getHeldItemMainhand().getItem() instanceof ItemAxe) {
                int harvestLevel = ((ItemAxe) ((EntityLivingBase) killer).getHeldItemMainhand().getItem()).getToolMaterial().getHarvestLevel();
                int amount = (int)Math.round(Math.random() * 2) + ((harvestLevel - 1) * 2);
                event.getEntity().dropItem(Items.LEATHER, amount);
                event.setCanceled(true);
            }
        }
    }
}
