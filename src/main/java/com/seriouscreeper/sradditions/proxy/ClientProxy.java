package com.seriouscreeper.sradditions.proxy;


import com.seriouscreeper.sradditions.init.ModBlocks;
import com.seriouscreeper.sradditions.init.ModItems;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        ModItems.registerRenders();
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        ModBlocks.registerRenders();
    }
}
