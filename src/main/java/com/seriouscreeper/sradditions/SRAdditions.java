package com.seriouscreeper.sradditions;

import com.seriouscreeper.sradditions.events.handlers.ForgeEventHandlers;
import com.seriouscreeper.sradditions.init.ModBlocks;
import com.seriouscreeper.sradditions.init.ModItems;
import com.seriouscreeper.sradditions.proxy.CommonProxy;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS)
public class SRAdditions {
    @Mod.Instance
    public static SRAdditions instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;


    public static BlockPos adventureTempPos1;
    public static BlockPos adventureTempPos2;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);

        ForgeEventHandlers forgeEventHandler = new ForgeEventHandlers();
        forgeEventHandler.register();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
