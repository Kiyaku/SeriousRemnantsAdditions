package com.seriouscreeper.sradditions.events.handlers;

import com.seriouscreeper.sradditions.SRAdditions;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;


public class EmbersFlightHandler {
    @SubscribeEvent
    public void onHasPotionEffect(TickEvent.PlayerTickEvent event) {
        PotionEffect effect = event.player.getActivePotionEffect(SRAdditions.flightPotion);

        if(effect != null) {
            if (effect.getDuration() == 0) {
                event.player.removePotionEffect(SRAdditions.flightPotion);
                event.player.capabilities.allowFlying = false;
            } else {
                if(!event.player.capabilities.allowFlying) {
                    System.out.println("WE HAVE THE EFFECT!");

                    event.player.capabilities.allowFlying = true;
                }
            }
        }
    }
}
