package com.seriouscreeper.sradditions.events.handlers;

import com.seriouscreeper.sradditions.SRAdditions;
import com.seriouscreeper.sradditions.renderer.RangeRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class ForgeEventHandlers {
    public void register()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onDrawWorld(RenderWorldLastEvent event) {
        if(SRAdditions.adventureTempPos1 != null) {
            GlStateManager.pushMatrix();

            RangeRenderer.renderRange(SRAdditions.adventureTempPos1, SRAdditions.adventureTempPos2);

            GlStateManager.popMatrix();
        }
    }
}
