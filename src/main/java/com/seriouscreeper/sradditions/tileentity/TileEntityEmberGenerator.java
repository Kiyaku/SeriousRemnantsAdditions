package com.seriouscreeper.sradditions.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import teamroots.embers.power.DefaultEmberCapability;
import teamroots.embers.power.EmberCapabilityProvider;
import teamroots.embers.power.IEmberCapability;

import javax.annotation.Nullable;


public class TileEntityEmberGenerator extends TileEntity implements IEnergyStorage, ITickable {
    public IEmberCapability emberCapability = new DefaultEmberCapability();
    private int energy = 0;

    public TileEntityEmberGenerator() {
        this.emberCapability.setEmberCapacity(24000.0D);
        this.emberCapability.setEmber(0.0D);
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public boolean canReceive() {
        return false;
    }

    @Override
    public int getMaxEnergyStored() {
        return 100000;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public int getEnergyStored() {
        return energy;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public void update() {
        if(this.hasWorld()) {
            final TileEntity tileEntity = this.getWorld().getTileEntity(this.getPos().offset(EnumFacing.UP));

            if(tileEntity != null && !tileEntity.isInvalid()) {
                if(tileEntity.hasCapability(CapabilityEnergy.ENERGY, EnumFacing.UP)) {
                    IEnergyStorage consumer = tileEntity.getCapability(CapabilityEnergy.ENERGY, EnumFacing.UP);

                    if(consumer != null && emberCapability.getEmber() >= 100 && consumer.receiveEnergy(1000, true) == 1000) {
                        emberCapability.removeAmount(100, true);
                        consumer.receiveEnergy(1000, false);
                    }
                }
            }
        }
    }

    public boolean hasCapability(Capability<?> capability, EnumFacing facing){
        if (capability == EmberCapabilityProvider.emberCapability){
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    public <T> T getCapability(Capability<T> capability, EnumFacing facing){
        if (capability == EmberCapabilityProvider.emberCapability){
            return (T)this.emberCapability;
        }
        return super.getCapability(capability, facing);
    }


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag){
        super.writeToNBT(tag);
        emberCapability.writeToNBT(tag);
        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag){
        super.readFromNBT(tag);
        emberCapability.readFromNBT(tag);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(getPos(), 0, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }
}
