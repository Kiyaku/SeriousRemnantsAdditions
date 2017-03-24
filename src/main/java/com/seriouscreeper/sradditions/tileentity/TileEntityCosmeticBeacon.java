package com.seriouscreeper.sradditions.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;


public class TileEntityCosmeticBeacon extends TileEntity {
    public List<BeamSegment> beaconSegments = new ArrayList<BeamSegment>();
    @SideOnly(Side.CLIENT)
    private float beamRenderScale;
    @SideOnly(Side.CLIENT)
    private long beamRenderCounter;
    private float[] color = new float[] {1, 1, 1 };


    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        if(compound.hasKey("r")) {
            color[0] = compound.getFloat("r");
            color[1] = compound.getFloat("g");
            color[2] = compound.getFloat("b");
        }

        updateBeacon();
    }


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setFloat("r", color[0]);
        compound.setFloat("g", color[1]);
        compound.setFloat("b", color[2]);

        return compound;
    }


    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        this.readFromNBT(packet.getNbtCompound());
    }


    @SideOnly(Side.CLIENT)
    public float shouldBeamRender()
    {
        int i = (int)(this.world.getTotalWorldTime() - this.beamRenderCounter);
        this.beamRenderCounter = this.world.getTotalWorldTime();

        if (i > 1)
        {
            this.beamRenderScale -= (float)i / 40.0F;

            if (this.beamRenderScale < 0.0F)
            {
                this.beamRenderScale = 0.0F;
            }
        }

        this.beamRenderScale += 0.025F;

        if (this.beamRenderScale > 1.0F)
        {
            this.beamRenderScale = 1.0F;
        }

        return this.beamRenderScale;
    }



    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        AxisAlignedBB newAxis = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY() + beamRenderCounter, pos.getZ());
        return newAxis;
    }


    public void ChangeColor(float[] color) {
        this.color = color;
        this.updateSegmentColors();
        markDirty();
        if (world != null) {
            IBlockState state = world.getBlockState(getPos());
            world.notifyBlockUpdate(getPos(), state, state, 3);
        }
    }


    private void updateSegmentColors()
    {
        int k = this.pos.getY();
        beaconSegments.clear();

        beaconSegments.clear();
        BeamSegment beamsegment = new BeamSegment(color);
        beaconSegments.add(beamsegment);

        for (int i1 = k + 1; i1 < 256; ++i1)
        {
            beamsegment.incrementHeight();
        }
    }


    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared()
    {
        return 65536.0D;
    }


    public void updateBeacon() {
        if (this.world != null)
        {
            this.updateSegmentColors();
        }
    }


    public static class BeamSegment
    {
        /** RGB (0 to 1.0) colors of this beam segment */
        private final float[] colors;
        private int height;

        public BeamSegment(float[] colorsIn)
        {
            this.colors = colorsIn;
            this.height = 1;
        }

        protected void incrementHeight()
        {
            ++this.height;
        }

        /**
         * Returns RGB (0 to 1.0) colors of this beam segment
         */
        public float[] getColors()
        {
            return this.colors;
        }

        @SideOnly(Side.CLIENT)
        public int getHeight()
        {
            return this.height;
        }
    }
}
