package com.seriouscreeper.sradditions;

import com.seriouscreeper.sradditions.init.ModBlocks;
import com.seriouscreeper.sradditions.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class SRAdditionsTab extends CreativeTabs {
    public SRAdditionsTab() {
        super("tabSAPAdditions");
    }

    @Override
    public ItemStack getTabIconItem() {
        return ItemStack.EMPTY;
    }
}
