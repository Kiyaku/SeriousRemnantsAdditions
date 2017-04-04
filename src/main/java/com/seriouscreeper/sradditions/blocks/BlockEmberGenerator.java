package com.seriouscreeper.sradditions.blocks;

import com.seriouscreeper.sradditions.tileentity.TileEntityAdventureBlock;
import com.seriouscreeper.sradditions.tileentity.TileEntityEmberGenerator;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;

/**
 * Created by Kiyaku on 4/2/2017.
 */
public class BlockEmberGenerator  extends BlockContainer implements ITileEntityProvider {
    public BlockEmberGenerator(String name, Material material) {
        super(material);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(CreativeTabs.MISC);
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
        GameRegistry.registerTileEntity(TileEntityEmberGenerator.class, "ember_generator");
    }


    public BlockEmberGenerator(String name) {
        this(name, Material.IRON);
    }


    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }


    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityEmberGenerator();
    }
}
