package com.seriouscreeper.sradditions.config;


import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler {
    public static Configuration config;

    public static boolean FiniteWater;
    public static boolean BottlesConsumeSourceBlock;


    public static void init(File file) {
        config = new Configuration(file);
        syncConfig();
    }


    public static void syncConfig() {
        String category;

        category = "Gameplay Changes";
        config.addCustomCategoryComment(category, "Finite Water");
        FiniteWater = config.getBoolean("finiteWater", category, true, "Disables creation of water source blocks");
        BottlesConsumeSourceBlock = config.getBoolean("bottlesConsumeSourceBlock", category, true, "Bottles consume water source block");

        config.save();
    }
}
