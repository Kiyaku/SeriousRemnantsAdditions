package com.seriouscreeper.sradditions.events.handlers;

import com.seriouscreeper.sradditions.config.ConfigHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.*;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class SleepHandler {
    @SubscribeEvent()
    public void onUseBed(PlayerSleepInBedEvent event) {
        World world = event.getEntityPlayer().getEntityWorld();

        if(getWorldHours(world) < ConfigHandler.BedTimeHour - 6) {
            event.setResult(EntityPlayer.SleepResult.OTHER_PROBLEM);
            EntityPlayer entityPlayer = event.getEntityPlayer();
            entityPlayer.sendStatusMessage(new TextComponentTranslation("sradditions.sleep.tooearly"), true);
        }
    }


    @SubscribeEvent
    public void onWakeUp(PlayerWakeUpEvent event) {
        if(ConfigHandler.SleepDebuffs && getWorldHours(event.getEntityPlayer().world) < ConfigHandler.BedTimeHour - 6) {
            EntityPlayer player = event.getEntityPlayer();

            player.getFoodStats().addStats(-ConfigHandler.LostFoodOnSleep, -ConfigHandler.LostFoodOnSleep);
            player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, ConfigHandler.SleepWeaknessDuration));
        }
    }


    private static int getWorldHours(World world) {
        int time = (int)Math.abs((world.getWorldTime()) % 24000);
        return (int)((float)time / 1000F);
    }
}
