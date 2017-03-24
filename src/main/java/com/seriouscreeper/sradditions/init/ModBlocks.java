package com.seriouscreeper.sradditions.init;

import com.seriouscreeper.sradditions.blocks.BlockAdventureBlock;
import com.seriouscreeper.sradditions.blocks.BlockCosmeticBeacon;
import com.seriouscreeper.sradditions.renderer.AdventureBlockRangeRenderer;
import com.seriouscreeper.sradditions.tileentity.TileEntityAdventureBlock;
import com.seriouscreeper.sradditions.tileentity.TileEntityCosmeticBeacon;
import com.seriouscreeper.sradditions.tileentity.TileEntityCosmeticBeaconRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Kiyaku on 2/15/2017.
 */
public class ModBlocks {
    public static Block blockAdventure;
    public static ItemBlock ibBlockAdventure;
    public static Block blockCosmeticBeacon;
    public static ItemBlock ibCosmeticBeacon;


    public static void init() {
        blockAdventure = new BlockAdventureBlock("adventure_block");
        ibBlockAdventure = new ItemBlock(blockAdventure);

        blockCosmeticBeacon = new BlockCosmeticBeacon("cosmetic_beacon");
        ibCosmeticBeacon = new ItemBlock(blockCosmeticBeacon);
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenders() {
        registerRender(blockAdventure);
        registerRender(blockCosmeticBeacon);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCosmeticBeacon.class, new TileEntityCosmeticBeaconRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAdventureBlock.class, new AdventureBlockRangeRenderer());
    }


    private static void registerRender(Block block) {
        Item item = Item.getItemFromBlock(block);
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
