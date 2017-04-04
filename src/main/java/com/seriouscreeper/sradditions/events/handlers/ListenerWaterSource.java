package com.seriouscreeper.sradditions.events.handlers;

import net.minecraft.block.material.Material;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.BiomeOcean;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;


public class ListenerWaterSource {
    @SubscribeEvent
    public void WaterGen(BlockEvent.CreateFluidSourceEvent event) {
        if(event.getWorld().getBlockState(event.getPos()).getMaterial() == Material.WATER && event.getWorld().getBiome(event.getPos()) != Biomes.DEEP_OCEAN) {
            event.setResult(Event.Result.DENY);
        }
    }
}
