package com.seriouscreeper.sradditions;

import com.seriouscreeper.sradditions.config.ConfigHandler;
import com.seriouscreeper.sradditions.events.handlers.*;
import com.seriouscreeper.sradditions.loot.ModLootTables;
import com.seriouscreeper.sradditions.potions.FlightPotion;
import com.seriouscreeper.sradditions.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.util.HashMap;


@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS)
public class SRAdditions {
    @Mod.Instance
    public static SRAdditions instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;


    public static BlockPos adventureTempPos1;
    public static BlockPos adventureTempPos2;
    public static Potion flightPotion = new FlightPotion(false, 0).setPotionName("potion.flightPotion");

    public static File configDir;

    public static HashMap<Block, Float> fallingBlocks = new HashMap<Block, Float>();
    public static HashMap<Block, Float> fracturingBlocks = new HashMap<Block, Float>();


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);

        ResourceLocation location = ModLootTables.SeriousLoot;

        configDir = new File(event.getModConfigurationDirectory() + "/seriousmods");
        configDir.mkdir();
        ConfigHandler.init(new File(configDir, Reference.MOD_ID + ".cfg"));

        ForgeEventListener forgeEventHandler = new ForgeEventListener();
        forgeEventHandler.register();

        if(ConfigHandler.FiniteWater) {
            FluidSourceListener fluidSourceListener = new FluidSourceListener();
            MinecraftForge.EVENT_BUS.register(fluidSourceListener);
        }

        if(ConfigHandler.BottlesConsumeSourceBlock) {
            RightClickBlockListener rightClickBlockListener = new RightClickBlockListener();
            MinecraftForge.EVENT_BUS.register(rightClickBlockListener);
        }

        if(ConfigHandler.AxeForLeather) {
            MoreLeatherHandler moreLeatherHandler = new MoreLeatherHandler();
            MinecraftForge.EVENT_BUS.register(moreLeatherHandler);
        }

        if(ConfigHandler.PreventEnchanting) {
            RightClickEnchantmentTable rightClickEnchantmentTable = new RightClickEnchantmentTable();
            MinecraftForge.EVENT_BUS.register(rightClickEnchantmentTable);
        }

        if(ConfigHandler.PreventEarlySleep) {
            SleepHandler sleepHandler = new SleepHandler();
            MinecraftForge.EVENT_BUS.register(sleepHandler);
        }

        if(ConfigHandler.EnableBlockFracturing || ConfigHandler.EnableBlockGravity) {
            FallingBlockListener fallingBlockListener = new FallingBlockListener();
            MinecraftForge.EVENT_BUS.register(fallingBlockListener);
        }


        //EmbersFlightListener embersFlightHandler = new EmbersFlightListener();
        //MinecraftForge.EVENT_BUS.register(embersFlightHandler);
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
