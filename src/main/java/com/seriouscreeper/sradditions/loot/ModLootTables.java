package com.seriouscreeper.sradditions.loot;

import com.seriouscreeper.sradditions.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;


public class ModLootTables {
    public static final ResourceLocation SeriousLoot = register("serious_loot");

    private static ResourceLocation register(String id) {
        return LootTableList.register(new ResourceLocation(Reference.MOD_ID, id));
    }
}
