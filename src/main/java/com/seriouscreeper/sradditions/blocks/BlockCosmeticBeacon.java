package com.seriouscreeper.sradditions.blocks;

import com.seriouscreeper.sradditions.SRAdditions;
import com.seriouscreeper.sradditions.tileentity.TileEntityCosmeticBeacon;
import com.seriouscreeper.sradditions.tileentity.TileEntityCosmeticBeaconRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameType;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;


public class BlockCosmeticBeacon extends BlockContainer {
    public BlockCosmeticBeacon(String name, Material material) {
        super(material);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(CreativeTabs.MISC);
        this.setBlockUnbreakable();
        this.setLightLevel(1.0F);
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
        GameRegistry.registerTileEntity(TileEntityCosmeticBeacon.class, "cosmetic_beacon");
    }


    public BlockCosmeticBeacon(String unlocalizedName) {
        this(unlocalizedName, Material.GLASS);
    }


    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }


    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }


    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityCosmeticBeacon();
    }


    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }


    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        TileEntity tileentity = world.getTileEntity(pos);

        if (tileentity instanceof TileEntityCosmeticBeacon)
        {
            ((TileEntityCosmeticBeacon)tileentity).updateBeacon();

            //world.addBlockEvent(pos, this, 1, 0);
        }
    }


    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(worldIn.isRemote) {
            return false;
        }

        EntityPlayerMP mp = (EntityPlayerMP)playerIn;

        if(mp.interactionManager.getGameType() != GameType.ADVENTURE && playerIn.getHeldItemMainhand() != ItemStack.EMPTY && playerIn.getHeldItemMainhand().getItem() instanceof ItemDye) {
            float[] color;

            color = EntitySheep.getDyeRgb(EnumDyeColor.byDyeDamage(playerIn.getHeldItemMainhand().getMetadata()));

            TileEntity tileEntity = worldIn.getTileEntity(pos);
            TileEntityCosmeticBeacon cosmeticBlock = (TileEntityCosmeticBeacon) tileEntity;

            if(cosmeticBlock != null)
                cosmeticBlock.ChangeColor(color);
        }

        return true;
    }
}
