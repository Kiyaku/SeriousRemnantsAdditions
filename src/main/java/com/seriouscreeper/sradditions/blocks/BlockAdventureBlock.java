package com.seriouscreeper.sradditions.blocks;

import com.seriouscreeper.sradditions.SRAdditions;
import com.seriouscreeper.sradditions.renderer.AdventureBlockRangeRenderer;
import com.seriouscreeper.sradditions.tileentity.TileEntityAdventureBlock;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

/**
 * Created by Kiyaku on 2/23/2017.
 */
public class BlockAdventureBlock extends BlockContainer implements ITileEntityProvider {
    public BlockAdventureBlock(String name, Material material) {
        super(material);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(CreativeTabs.MISC);
        this.setBlockUnbreakable();
        this.setResistance(500);
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
        GameRegistry.registerTileEntity(TileEntityAdventureBlock.class, "adventure_block");
    }


    public BlockAdventureBlock(String name) {
        this(name, Material.GLASS);
    }


    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }


    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }


    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityAdventureBlock();
    }


    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote && playerIn.isCreative() && hand == EnumHand.MAIN_HAND) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            TileEntityAdventureBlock adventureBlock = (TileEntityAdventureBlock) tileEntity;

            if(adventureBlock != null) {
                if(playerIn.getHeldItemMainhand() == ItemStack.EMPTY) {
                    if (playerIn.isSneaking()) {
                        int newLength = adventureBlock.ChangeDistance(facing, -1);
                        playerIn.sendMessage(new TextComponentString("Lowered " + facing + " distance to " + newLength));
                    } else {
                        int newLength = adventureBlock.ChangeDistance(facing, 1);
                        playerIn.sendMessage(new TextComponentString("Increased " + facing + " distance to " + newLength));
                    }
                } else {
                    System.out.println("toggle render box");
                    adventureBlock.ToggleRenderer();
                }
            }
        }

        return true;
    }
}
