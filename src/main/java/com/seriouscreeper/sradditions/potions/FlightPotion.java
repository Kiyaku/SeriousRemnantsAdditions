package com.seriouscreeper.sradditions.potions;

import net.minecraft.potion.Potion;


public class FlightPotion extends Potion {
    private boolean isBad = false;

    public FlightPotion(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
    }


    @Override
    public boolean isBadEffect() {
        return isBad;
    }
}