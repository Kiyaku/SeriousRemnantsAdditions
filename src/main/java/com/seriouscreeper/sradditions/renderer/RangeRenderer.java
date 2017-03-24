package com.seriouscreeper.sradditions.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityBeaconRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

/**
 * Created by Kiyaku on 2/27/2017.
 */
@SideOnly(Side.CLIENT)
public class RangeRenderer {
    private static void renderArea(BlockPos pos1, BlockPos pos2, float sizeP)
    {
        drawCuboid(pos1, pos2, sizeP);
    }

    @SideOnly(Side.CLIENT)
    private static void drawCuboid(BlockPos min, BlockPos max, float sizeP)
    {
        float width2 = ((float) max.getX() - (float) min.getX()) * 0.5f;
        float height2 = ((float) max.getY() - (float) min.getY()) * 0.5f;
        float length2 = ((float) max.getZ() - (float) min.getZ()) * 0.5f;

        double centerX = min.getX() + width2;
        double centerY = min.getY() + height2;
        double centerZ = min.getZ() + length2;

        int sizeCE = -1;

        GlStateManager.pushMatrix();
        GlStateManager.translate(centerX, centerY, centerZ);
        drawCuboid(Tessellator.getInstance().getBuffer(), width2 * sizeCE + sizeP, height2 * sizeCE + sizeP, length2 * sizeCE + sizeP, 1);
        GlStateManager.popMatrix();
    }


    public static void drawCuboid(VertexBuffer renderer, float sizeX, float sizeY, float sizeZ, float in)
    {
        renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

        renderer.pos(-sizeX * in, -sizeY * in, -sizeZ).tex(0, 0).endVertex();
        renderer.pos(-sizeX * in, sizeY * in, -sizeZ).tex(0, 1).endVertex();
        renderer.pos(sizeX * in, sizeY * in, -sizeZ).tex(1, 1).endVertex();
        renderer.pos(sizeX * in, -sizeY * in, -sizeZ).tex(1, 0).endVertex();

        renderer.pos(-sizeX * in, -sizeY * in, sizeZ).tex(0, 0).endVertex();
        renderer.pos(sizeX * in, -sizeY * in, sizeZ).tex(1, 0).endVertex();
        renderer.pos(sizeX * in, sizeY * in, sizeZ).tex(1, 1).endVertex();
        renderer.pos(-sizeX * in, sizeY * in, sizeZ).tex(0, 1).endVertex();

        renderer.pos(-sizeX, -sizeY * in, -sizeZ * in).tex(0, 0).endVertex();
        renderer.pos(-sizeX, -sizeY * in, sizeZ * in).tex(0, 1).endVertex();
        renderer.pos(-sizeX, sizeY * in, sizeZ * in).tex(1, 1).endVertex();
        renderer.pos(-sizeX, sizeY * in, -sizeZ * in).tex(1, 0).endVertex();

        renderer.pos(sizeX, -sizeY * in, -sizeZ * in).tex(0, 0).endVertex();
        renderer.pos(sizeX, sizeY * in, -sizeZ * in).tex(0, 1).endVertex();
        renderer.pos(sizeX, sizeY * in, sizeZ * in).tex(1, 1).endVertex();
        renderer.pos(sizeX, -sizeY * in, sizeZ * in).tex(1, 0).endVertex();

        renderer.pos(-sizeX * in, sizeY, -sizeZ * in).tex(0, 0).endVertex();
        renderer.pos(-sizeX * in, sizeY, sizeZ * in).tex(0, 1).endVertex();
        renderer.pos(sizeX * in, sizeY, sizeZ * in).tex(1, 1).endVertex();
        renderer.pos(sizeX * in, sizeY, -sizeZ * in).tex(1, 0).endVertex();

        renderer.pos(-sizeX * in, -sizeY, -sizeZ * in).tex(0, 0).endVertex();
        renderer.pos(sizeX * in, -sizeY, -sizeZ * in).tex(1, 0).endVertex();
        renderer.pos(sizeX * in, -sizeY, sizeZ * in).tex(1, 1).endVertex();
        renderer.pos(-sizeX * in, -sizeY, sizeZ * in).tex(0, 1).endVertex();

        Tessellator.getInstance().draw();
    }


    public static void renderRange(BlockPos pos1, BlockPos pos2) {
        GlStateManager.color(0.4f, 0.65f, 0.8f);
        GlStateManager.enableBlend();
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.0001f);

        Minecraft.getMinecraft().renderEngine.bindTexture(TileEntityBeaconRenderer.TEXTURE_BEACON_BEAM);

        GlStateManager.color(0.2f, 0.5f, 0.6f, 0.5f);
        renderArea(pos1, pos2, 0.01f);

        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.002f);
        GlStateManager.disableBlend();
    }
}
