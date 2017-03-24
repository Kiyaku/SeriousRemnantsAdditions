package com.seriouscreeper.sradditions.renderer;

import com.seriouscreeper.sradditions.Reference;
import com.seriouscreeper.sradditions.tileentity.TileEntityAdventureBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityBeaconRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class AdventureBlockRangeRenderer extends TileEntitySpecialRenderer<TileEntityAdventureBlock> {
    public static final ResourceLocation TEXTURE_RANGE = new ResourceLocation(Reference.MOD_ID, "textures/blocks/cosmetic_beacon.png");

    @Override
    public void renderTileEntityAt(TileEntityAdventureBlock te, double x, double y, double z, float partialTicks, int destroyStage) {
        if(te.renderBox == false)
            return;

        GlStateManager.alphaFunc(516, 0.01F);
        this.bindTexture(TileEntityBeaconRenderer.TEXTURE_BEACON_BEAM);

        Minecraft mc = Minecraft.getMinecraft();
        int ticks = mc.player.ticksExisted;
        Entity renderEntity = mc.getRenderViewEntity();
        double entityX = renderEntity.posX - te.getPos().getX();
        double entityY = renderEntity.posY - te.getPos().getY();
        double entityZ = renderEntity.posZ - te.getPos().getZ();

        GlStateManager.pushMatrix();
        GlStateManager.translate(-entityX, -entityY, -entityZ);

        GlStateManager.disableFog();

        GlStateManager.glTexParameteri(3553, 10242, 10497);
        GlStateManager.glTexParameteri(3553, 10243, 10497);
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.depthMask(true);

        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer renderer = tessellator.getBuffer();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);

        BlockPos pos1 = new BlockPos(te.pos1.getX() + 1, te.pos1.getY() + 1, te.pos1.getZ() + 1);
        BlockPos pos2 = new BlockPos(te.pos2.getX(), te.pos2.getY(), te.pos2.getZ());
        
        float[] col = new float[] {1, 1, 1};
        float alpha = 0.5f;

        renderer.pos(-pos2.getX(), -pos2.getY(), -pos2.getZ()).tex(0, 0).color(col[0], col[1], col[2], alpha).endVertex();
        renderer.pos(-pos2.getX(), pos1.getY(), -pos2.getZ()).tex(0, 1).color(col[0], col[1], col[2], alpha).endVertex();
        renderer.pos(pos1.getX(), pos1.getY(), -pos2.getZ()).tex(1, 1).color(col[0], col[1], col[2], alpha).endVertex();
        renderer.pos(pos1.getX(), -pos2.getY(), -pos2.getZ()).tex(1, 0).color(col[0], col[1], col[2], alpha).endVertex();

        renderer.pos(-pos2.getX(), -pos2.getY(), pos1.getZ()).tex(0, 0).color(col[0], col[1], col[2], alpha).endVertex();
        renderer.pos(pos1.getX(), -pos2.getY(), pos1.getZ()).tex(1, 0).color(col[0], col[1], col[2], alpha).endVertex();
        renderer.pos(pos1.getX(), pos1.getY(), pos1.getZ()).tex(1, 1).color(col[0], col[1], col[2], alpha).endVertex();
        renderer.pos(-pos2.getX(), pos1.getY(), pos1.getZ()).tex(0, 1).color(col[0], col[1], col[2], alpha).endVertex();

        renderer.pos(-pos2.getX(), -pos2.getY(), -pos2.getZ()).tex(0, 0).color(col[0], col[1], col[2], alpha).endVertex();
        renderer.pos(-pos2.getX(), -pos2.getY(), pos1.getZ()).tex(0, 1).color(col[0], col[1], col[2], alpha).endVertex();
        renderer.pos(-pos2.getX(), pos1.getY(), pos1.getZ()).tex(1, 1).color(col[0], col[1], col[2], alpha).endVertex();
        renderer.pos(-pos2.getX(), pos1.getY(), -pos2.getZ()).tex(1, 0).color(col[0], col[1], col[2], alpha).endVertex();

        renderer.pos(pos1.getX(), -pos2.getY(), -pos2.getZ()).tex(0, 0).color(col[0], col[1], col[2], alpha).endVertex();
        renderer.pos(pos1.getX(), pos1.getY(), -pos2.getZ()).tex(0, 1).color(col[0], col[1], col[2], alpha).endVertex();
        renderer.pos(pos1.getX(), pos1.getY(), pos1.getZ()).tex(1, 1).color(col[0], col[1], col[2], alpha).endVertex();
        renderer.pos(pos1.getX(), -pos2.getY(), pos1.getZ()).tex(1, 0).color(col[0], col[1], col[2], alpha).endVertex();

        renderer.pos(-pos2.getX(), pos1.getY(), -pos2.getZ()).tex(0, 0).color(col[0], col[1], col[2], alpha).endVertex();
        renderer.pos(-pos2.getX(), pos1.getY(), pos1.getZ()).tex(0, 1).color(col[0], col[1], col[2], alpha).endVertex();
        renderer.pos(pos1.getX(), pos1.getY(), pos1.getZ()).tex(1, 1).color(col[0], col[1], col[2], alpha).endVertex();
        renderer.pos(pos1.getX(), pos1.getY(), -pos2.getZ()).tex(1, 0).color(col[0], col[1], col[2], alpha).endVertex();

        renderer.pos(-pos2.getX(), -pos2.getY(), -pos2.getZ()).tex(0, 0).color(col[0], col[1], col[2], alpha).endVertex();
        renderer.pos(pos1.getX(), -pos2.getY(), -pos2.getZ()).tex(1, 0).color(col[0], col[1], col[2], alpha).endVertex();
        renderer.pos(pos1.getX(), -pos2.getY(), pos2.getZ()).tex(1, 1).color(col[0], col[1], col[2], alpha).endVertex();
        renderer.pos(-pos2.getX(), -pos2.getY(), pos2.getZ()).tex(0, 1).color(col[0], col[1], col[2], alpha).endVertex();

        tessellator.draw();
        GlStateManager.enableBlend();
        GlStateManager.enableLighting();
        GlStateManager.depthMask(false);

        GlStateManager.enableFog();

        GlStateManager.popMatrix();
    }


    @Override
    public boolean isGlobalRenderer(TileEntityAdventureBlock te) {
        return true;
    }
}
