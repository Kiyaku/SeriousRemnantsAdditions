package com.seriouscreeper.sradditions.recipes;


import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import teamroots.embers.RegistryManager;
import teamroots.embers.recipe.ItemMeltingRecipe;
import teamroots.embers.recipe.RecipeRegistry;

public class EmbersExtraRecipes {
    public static void AddExtraRecipes() {
        RecipeRegistry.meltingRecipes.add(new ItemMeltingRecipe(new ItemStack(Item.getByNameOrId("survivalist:rock_ore")), new FluidStack(RegistryManager.fluid_molten_iron, 96), true, false));
        RecipeRegistry.meltingRecipes.add(new ItemMeltingRecipe(new ItemStack(Item.getByNameOrId("survivalist:rock_ore"), 1, 1), new FluidStack(RegistryManager.fluid_molten_gold, 96), true, false));
        RecipeRegistry.meltingRecipes.add(new ItemMeltingRecipe(new ItemStack(Item.getByNameOrId("survivalist:rock_ore"), 1, 2), new FluidStack(RegistryManager.fluid_molten_copper, 96), true, false));
        RecipeRegistry.meltingRecipes.add(new ItemMeltingRecipe(new ItemStack(Item.getByNameOrId("survivalist:rock_ore"), 1, 4), new FluidStack(RegistryManager.fluid_molten_lead, 96), true, false));
        RecipeRegistry.meltingRecipes.add(new ItemMeltingRecipe(new ItemStack(Item.getByNameOrId("survivalist:rock_ore"), 1, 5), new FluidStack(RegistryManager.fluid_molten_silver, 96), true, false));
    }
}
