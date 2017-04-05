package com.seriouscreeper.sradditions.config;


import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler {
    public static Configuration config;

    public static boolean FiniteWater;
    public static boolean BottlesConsumeSourceBlock;
    public static boolean AxeForLeather;
    public static boolean PreventEnchanting;
    public static int AdventureBlockUpdateRate;


    public static void init(File file) {
        config = new Configuration(file);
        syncConfig();
    }


    public static void syncConfig() {
        String category;

        category = "Gameplay Changes";
        FiniteWater = config.getBoolean("finiteWater", category, true, "Disables creation of water source blocks");
        BottlesConsumeSourceBlock = config.getBoolean("bottlesConsumeSourceBlock", category, true, "Bottles consume water source block");
        AxeForLeather = config.getBoolean("axeForLeather", category, true, "Killing cows with an axe drops more leather but no beef");
        PreventEnchanting = config.getBoolean("preventEnchanting", category, true, "Disables the use of the enchanting table");
        AdventureBlockUpdateRate = config.getInt("adventureBlockUpdateRate", category, 20, 5, 20, "How often the adventure block checks for players, in ticks");

        config.save();
    }
}
