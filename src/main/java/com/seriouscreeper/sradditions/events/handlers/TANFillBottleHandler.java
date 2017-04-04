package com.seriouscreeper.sradditions.events.handlers;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import toughasnails.handler.thirst.FillBottleHandler;

/**
 * Created by Kiyaku on 3/24/2017.
 */
public class TANFillBottleHandler extends FillBottleHandler {
    @Override
    public void onPlayerRightClickWater(PlayerInteractEvent.RightClickItem event) throws Exception {
        System.out.println("CUSTOM EVENT");

        if(event.getResult() == Event.Result.ALLOW) {
            System.out.println("SUCCESS");
        }
    }
}
