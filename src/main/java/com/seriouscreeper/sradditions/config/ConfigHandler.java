package com.seriouscreeper.sradditions.config;


import com.seriouscreeper.sradditions.SRAdditions;
import net.minecraft.block.Block;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler {
    public static Configuration config;

    public static boolean FiniteWater;
    public static boolean BottlesConsumeSourceBlock;
    public static boolean AxeForLeather;
    public static boolean PreventEnchanting;
    public static int AdventureBlockUpdateRate;

    public static boolean PreventEarlySleep;
    public static int BedTimeHour;
    public static int LostFoodOnSleep;
    public static int SleepWeaknessDuration;
    public static boolean SleepDebuffs;

    public static boolean EnableBlockGravity;
    public static boolean EnableBlockFracturing;
    public static boolean GravityBlocksHurt;


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

        category = "Sleep Changes";
        PreventEarlySleep = config.getBoolean("preventEarlySleep", category, true, "Prevents sleep until [BedTimeHour]");
        BedTimeHour = config.getInt("bedTimeHour", category, 22, 0, 23, "At what time the player can sleep, in hours");
        LostFoodOnSleep = config.getInt("lostFoodOnSleep", category, 14, 0, 20, "How much food and saturation player looses when sleeping");
        SleepWeaknessDuration = config.getInt("sleepWeaknessDuration", category, 2400, 0, Integer.MAX_VALUE, "How long the weakness debuff lasts after sleeping");
        SleepDebuffs = config.getBoolean("sleepDebuffs", category, true, "Apply debuffs when sleeping");

        category = "Gravity Blocks";
        EnableBlockGravity = config.getBoolean("enableBlockGravity", category, true, "Enable block gravity");
        EnableBlockFracturing = config.getBoolean("enableBlockFracturing", category, true, "Enable block fracturing");
        GravityBlocksHurt = config.getBoolean("gravityBlocksHurt", category, true, "Falling blocks hurt players");

        String[] gravityBlocks = config.getStringList("gravityBlocks", category, new String[] {"minecraft:stone@0.1", "minecraft:cobblestone@0.6", "minecraft:log@0.3", "minecraft:log2@0.3", "minecraft:leaves@0.6", "minecraft:dirt@0.4", "minecraft:grass@0.2"}, "List of blocks and their chance of falling (blockname@chance)");
        if(EnableBlockGravity) {
            for(String blocks : gravityBlocks) {
                Block block = Block.getBlockFromName(blocks.split("@")[0]);
                float chance = Float.parseFloat(blocks.split("@")[1]);
                SRAdditions.fallingBlocks.put(block, chance);
            }
        }

        String[] fracturingBlocks = config.getStringList("fracturingBlocks", category, new String[] {"minecraft:stone@0.8", "minecraft:cobblestone@0.4"}, "List of blocks that can fracture neighbour blocks and their chance of falling (blockname@chance)");
        if(EnableBlockFracturing) {
            for(String blocks : fracturingBlocks) {
                Block block = Block.getBlockFromName(blocks.split("@")[0]);
                float chance = Float.parseFloat(blocks.split("@")[1]);
                SRAdditions.fracturingBlocks.put(block, chance);
            }
        }

        config.save();
    }
}
