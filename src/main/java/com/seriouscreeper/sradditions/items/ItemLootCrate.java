package com.seriouscreeper.sradditions.items;

import com.seriouscreeper.sradditions.Reference;
import com.seriouscreeper.sradditions.loot.ModLootTables;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ItemLootCrate extends Item {
    private static String[] poolNames = new String[] {"common", "uncommon", "rare", "mythic"};

    public ItemLootCrate(String unlocalizedName) {
        super();
        setHasSubtypes(true);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(unlocalizedName);
        setCreativeTab(CreativeTabs.MISC);
    }


    @Override
    public boolean hasEffect(ItemStack stack) {
        if(stack.getMetadata() == 3)
            return true;

        return super.hasEffect(stack);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "_" + stack.getMetadata();
    }


    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        subItems.add(new ItemStack(itemIn, 1, 0));
        subItems.add(new ItemStack(itemIn, 1, 1));
        subItems.add(new ItemStack(itemIn, 1, 2));
        subItems.add(new ItemStack(itemIn, 1, 3));
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if(!worldIn.isRemote) {
            String poolName = poolNames[playerIn.getHeldItemMainhand().getMetadata()];
            ResourceLocation location = ModLootTables.SeriousLoot;
            LootTable table = worldIn.getLootTableManager().getLootTableFromLocation(location);
            Random random = new Random();
            LootContext.Builder lootcontext$builder = new LootContext.Builder((WorldServer)worldIn);
            LootPool pool = table.getPool(poolName);
            List<ItemStack> items = new ArrayList<>();
            pool.generateLoot(items, random, lootcontext$builder.build());

            if(items.size() > 0) {
                for (ItemStack item : items) {
                    if (!playerIn.inventory.addItemStackToInventory(item)) {
                        worldIn.spawnEntity(new EntityItem(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, item));
                    }
                }

                playerIn.inventory.decrStackSize(playerIn.inventory.currentItem, 1);
            }
        } else {
            worldIn.playSound(playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.NEUTRAL, 1.0f, itemRand.nextFloat() + 1.0f, false);
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
