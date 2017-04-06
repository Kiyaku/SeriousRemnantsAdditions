package com.seriouscreeper.sradditions.proxy;


import com.seriouscreeper.sradditions.init.ModBlocks;
import com.seriouscreeper.sradditions.init.ModItems;
import com.seriouscreeper.sradditions.recipes.EmbersExtraRecipes;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
        // Initialization of blocks and items typically goes here:
        ModBlocks.init();
        ModItems.init();
    }

    public void init(FMLInitializationEvent e) {
        EmbersExtraRecipes.AddExtraRecipes();
    }

    public void postInit(FMLPostInitializationEvent e) {

    }
}
