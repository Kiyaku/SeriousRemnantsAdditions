package com.seriouscreeper.sradditions.proxy;


import com.seriouscreeper.sradditions.init.ModBlocks;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        // Typically initialization of models and such goes here:
        ModBlocks.registerRenders();
    }
}
