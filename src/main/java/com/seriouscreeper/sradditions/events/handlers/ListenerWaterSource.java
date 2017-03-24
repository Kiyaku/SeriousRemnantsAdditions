package com.seriouscreeper.sradditions.events.handlers;

import net.minecraft.block.material.Material;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class ListenerWaterSource {
    @SubscribeEvent
    public void WaterGen(BlockEvent.CreateFluidSourceEvent event) {
        if(event.getWorld().getBlockState(event.getPos()).getMaterial() == Material.WATER) {
            event.setResult(Event.Result.DENY);
        }
    }
}
