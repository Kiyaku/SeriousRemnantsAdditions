package com.seriouscreeper.sradditions.init;

import com.seriouscreeper.sradditions.Reference;
import com.seriouscreeper.sradditions.items.ItemLootCrate;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {
    public static Item itemLootCrate;

    public static void init() {
        itemLootCrate = new ItemLootCrate("loot_crate");
        GameRegistry.register(itemLootCrate);
    }

    public static void registerRenders() {
        registerRender(itemLootCrate, 0);
        registerRender(itemLootCrate, 1);
        registerRender(itemLootCrate, 2);
        registerRender(itemLootCrate, 3);
    }

    private static void registerRender(Item item, int meta) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Reference.MOD_ID + ":loot_crate_" + meta, "inventory"));
    }
}
