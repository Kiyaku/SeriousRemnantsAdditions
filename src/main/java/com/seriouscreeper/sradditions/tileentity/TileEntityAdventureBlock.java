package com.seriouscreeper.sradditions.tileentity;

import com.seriouscreeper.sradditions.SRAdditions;
import com.seriouscreeper.sradditions.config.ConfigHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TileEntityAdventureBlock extends TileEntity implements ITickable {
    private int tick = 0;
    private List<UUID> tempPlayers = new ArrayList<UUID>();
    private String titleString = "";
    private String subTitleString = "";
    public BlockPos pos1 = new BlockPos(4, 4, 4); // east, south, up
    public BlockPos pos2 = new BlockPos(4, 4, 4); // west, north, down
    public boolean renderBox = false;


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

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setString("title", titleString);
        compound.setString("subtitle", subTitleString);
        compound.setInteger("pos1X", pos1.getX());
        compound.setInteger("pos1Y", pos1.getY());
        compound.setInteger("pos1Z", pos1.getZ());
        compound.setInteger("pos2X", pos2.getX());
        compound.setInteger("pos2Y", pos2.getY());
        compound.setInteger("pos2Z", pos2.getZ());
        compound.setBoolean("renderBox", renderBox);

        return compound;
    }


    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        if(compound.hasKey("pos1X")) {
            pos1 = new BlockPos(compound.getInteger("pos1X"), compound.getInteger("pos1Y"), compound.getInteger("pos1Z"));
            pos2 = new BlockPos(compound.getInteger("pos2X"), compound.getInteger("pos2Y"), compound.getInteger("pos2Z"));
        }

        if(compound.hasKey("title")) {
            titleString = compound.getString("title");
        }

        if(compound.hasKey("subtitle")) {
            subTitleString = compound.getString("subtitle");
        }

        if(compound.hasKey("renderBox")) {
            renderBox = compound.getBoolean("renderBox");
        }
    }


    public int ChangeDistance(EnumFacing dir, int val) {
        int newLength = 0;

        if(dir.equals(EnumFacing.UP))    { pos1 = pos1.add(0, val, 0); newLength = pos1.getY(); }
        if(dir.equals(EnumFacing.DOWN))  { pos2 = pos2.add(0, val, 0); newLength = pos2.getY(); }
        if(dir.equals(EnumFacing.NORTH)) { pos2 = pos2.add(0, 0, val); newLength = pos2.getZ(); }
        if(dir.equals(EnumFacing.SOUTH)) { pos1 = pos1.add(0, 0, val); newLength = pos1.getZ(); }
        if(dir.equals(EnumFacing.WEST))  { pos2 = pos2.add(val, 0, 0); newLength = pos2.getX(); }
        if(dir.equals(EnumFacing.EAST))  { pos1 = pos1.add(val, 0, 0); newLength = pos1.getX(); }

        markDirty();

        if (world != null) {
            IBlockState state = world.getBlockState(getPos());
            world.notifyBlockUpdate(getPos(), state, state, 3);
        }

        return newLength;
    }


    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        AxisAlignedBB newAxis = new AxisAlignedBB(pos.getX() + pos1.getX(), pos.getY() + pos1.getY(), pos.getZ() + pos1.getZ(), pos.getX() - pos2.getX(), pos.getY() - pos2.getY(), pos.getZ() - pos2.getZ());
        return newAxis;
    }


    public void ToggleRenderer() {
        renderBox = !renderBox;

        markDirty();

        if (world != null) {
            IBlockState state = world.getBlockState(getPos());
            world.notifyBlockUpdate(getPos(), state, state, 3);
        }
    }


    private boolean isWithinArea(BlockPos playerPos) {
        return (playerPos.getX() <= pos.getX() + pos1.getX() && playerPos.getY() <= pos.getY() + pos1.getY() && playerPos.getZ() <= pos.getZ() + pos1.getZ() &&
                playerPos.getX() >= pos.getX() - pos2.getX() && playerPos.getY() >= pos.getY() - pos2.getY() && playerPos.getZ() >= pos.getZ() - pos2.getZ());
    }


    @Override
    public void update() {
        if(world.isRemote) {
            return;
        }

        tick++;

        if(tick % ConfigHandler.AdventureBlockUpdateRate == 0) {
            tick = 0;

            List<EntityPlayer> players = world.playerEntities;

            for(int i = 0; i < players.size(); i++) {
                EntityPlayer tempPlayer = players.get(i);

                if (!tempPlayers.contains(tempPlayer.getUniqueID()) && !tempPlayer.isCreative() && isWithinArea(tempPlayer.getPosition())) {
                    tempPlayers.add(tempPlayer.getUniqueID());
                    tempPlayer.setGameType(GameType.ADVENTURE);
                    tempPlayer.sendStatusMessage(new TextComponentTranslation("sradditions.switched_adventuremode"), true);

                    if (!titleString.isEmpty()) {
                        world.getMinecraftServer().commandManager.executeCommand(world.getMinecraftServer(), "/title " + tempPlayer.getName() + " times 5 60 5");
                        world.getMinecraftServer().commandManager.executeCommand(world.getMinecraftServer(), "/title " + tempPlayer.getName() + " title {\"text\": \"" + titleString + "\"}");
                    }

                    if (!subTitleString.isEmpty()) {
                        world.getMinecraftServer().commandManager.executeCommand(world.getMinecraftServer(), "/title " + tempPlayer.getName() + " subtitle {\"text\": \"" + subTitleString + "\"}");
                    }
                }
            }

            for(int i = 0; i < tempPlayers.size(); i++) {
                UUID uuid = tempPlayers.get(i);
                EntityPlayer tempPlayer = world.getPlayerEntityByUUID(uuid);

                if(tempPlayer != null && !isWithinArea(tempPlayer.getPosition())) {
                    tempPlayers.remove(tempPlayer.getUniqueID());

                    if(!tempPlayer.isCreative() && !tempPlayer.isDead) {
                        tempPlayer.sendStatusMessage(new TextComponentTranslation("sradditions.switched_survivalmode"), true);
                        tempPlayer.setGameType(GameType.SURVIVAL);

                        if(!titleString.isEmpty()) {
                            world.getMinecraftServer().commandManager.executeCommand(tempPlayer, "/title " + tempPlayer.getName() + " title {\"text\": \"Leaving area\"}");
                        }
                    }
                }
            }
        }
    }
}
