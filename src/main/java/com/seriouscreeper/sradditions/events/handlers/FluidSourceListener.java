package com.seriouscreeper.sradditions.events.handlers;

import net.minecraft.block.material.Material;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.BiomeOcean;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;


public class FluidSourceListener {
    @SubscribeEvent
    public void FluidSource(BlockEvent.CreateFluidSourceEvent event) {
        Material material = event.getWorld().getBlockState(event.getPos()).getMaterial();

        if(material == Material.WATER && event.getWorld().getBiome(event.getPos()) != Biomes.DEEP_OCEAN) {
            event.setResult(Event.Result.DENY);
        } else if(material == Material.LAVA && event.getWorld().getBiome(event.getPos()) == Biomes.HELL) {
            event.setResult(Event.Result.ALLOW);
        }
    }
}
