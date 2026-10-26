package com.supersouper.whichery.common.tileentities;

import java.util.Iterator;
import java.util.List;

import com.supersouper.whichery.common.BrewEffects.Brew;
import com.supersouper.whichery.common.BrewEffects.BrewHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import com.supersouper.whichery.ModBlocks;

public class LiquidTileEntity extends TileEntity {

    public Brew brew;
    public int ticksRemaining = 100;

    @Override
    public void updateEntity() {
        int x = this.xCoord;
        int y = this.yCoord;
        int z = this.zCoord;


        if (brew != null) {

            if (ticksRemaining % 20 == 0) {
                // gets adjacent tile entities
                LiquidTileEntity posXTileEntity = (LiquidTileEntity)this.worldObj.getTileEntity(x + 1, y, z);
                LiquidTileEntity negXTileEntity = (LiquidTileEntity)this.worldObj.getTileEntity(x - 1, y, z);
                LiquidTileEntity negYTileEntity = (LiquidTileEntity)this.worldObj.getTileEntity(x, y - 1, z);
                LiquidTileEntity posZTileEntity = (LiquidTileEntity)this.worldObj.getTileEntity(x, y, z + 1);
                LiquidTileEntity negZTileEntity = (LiquidTileEntity)this.worldObj.getTileEntity(x, y, z - 1);

                // gets adjacent blocks

                Block posXBlock = this.worldObj.getBlock(x + 1, y, z);
                Block negXBlock = this.worldObj.getBlock(x - 1, y, z);
                Block negYBlock = this.worldObj.getBlock(x, y - 1, z);
                Block posZBlock = this.worldObj.getBlock(x, y, z + 1);
                Block negZBlock = this.worldObj.getBlock(x, y, z - 1);


                // checks orthogonal directions and spreads the tile entity incase there are any adjacent blank tiles
                if (posXTileEntity != null && posXBlock == ModBlocks.BREW_FLUID.get() && posXTileEntity.getBrew() == null) {

                    posXTileEntity.setBrew(this.brew);
                    posXTileEntity.setTicksRemaining(this.ticksRemaining);

                }
                if (negXTileEntity != null && negXBlock == ModBlocks.BREW_FLUID.get() && negXTileEntity.getBrew() == null) {

                    negXTileEntity.setBrew(this.brew);
                    negXTileEntity.setTicksRemaining(this.ticksRemaining);

                }
                if (negYTileEntity != null && negYBlock == ModBlocks.BREW_FLUID.get() && negYTileEntity.getBrew() == null) {

                    negYTileEntity.setBrew(this.brew);
                    negYTileEntity.setTicksRemaining(this.ticksRemaining);

                }
                if (posZTileEntity != null && posZBlock == ModBlocks.BREW_FLUID.get() && posZTileEntity.getBrew() == null) {

                    posZTileEntity.setBrew(this.brew);
                    posZTileEntity.setTicksRemaining(this.ticksRemaining);

                }
                if (negZTileEntity != null && negZBlock == ModBlocks.BREW_FLUID.get() && negZTileEntity.getBrew() == null) {

                    negZTileEntity.setBrew(this.brew);
                    negZTileEntity.setTicksRemaining(this.ticksRemaining);

                }

                // handles effects on entities
                AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
                List<EntityLivingBase> list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
                Iterator<EntityLivingBase> iterator = list.iterator();
                EntityLivingBase entity;
                while (iterator.hasNext()) {
                    entity = iterator.next();
                    // call to entity effect
                    brew.onEntity(entity);
                }


                // code to affect adjacent blocks
                brew.onBlock(this.worldObj, x + 1,y,z);
                brew.onBlock(this.worldObj, x - 1,y,z);
                brew.onBlock(this.worldObj, x,y + 1,z);
                brew.onBlock(this.worldObj, x,y - 1,z);
                brew.onBlock(this.worldObj, x,y,z + 1);
                brew.onBlock(this.worldObj, x,y,z - 1);

            }
            ticksRemaining -= 1;
        }
        if (ticksRemaining <= 0) {

            this.worldObj.setBlockToAir(x, y, z);

        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {

        super.readFromNBT(compound);

        if (this.brew != null) {
            this.brew.id = compound.getString("brew-id");
            this.brew.dispersal = compound.getInteger("dispersal");
            this.brew.duration = compound.getInteger("duration");
            this.brew.linger = compound.getInteger("linger");
            this.brew.power = compound.getInteger("power");
            this.brew.speed = compound.getInteger("speed");
            this.brew.type = compound.getInteger("type");
            this.ticksRemaining = compound.getInteger("ticksRemaining");
        } else {
            this.brew = BrewHelper.createBrewFromID(
                compound.getString("brew-id"),
                compound.getInteger("power"),
                compound.getInteger("duration"),
                compound.getInteger("speed"),
                compound.getInteger("dispersal"),
                compound.getInteger("linger"),
                compound.getInteger("type")
            );
            this.ticksRemaining = compound.getInteger("ticksRemaining");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {

        super.writeToNBT(compound);

        if (this.brew != null) {
            compound.setString("brew-id",this.brew.id);
            compound.setInteger("dispersal", this.brew.dispersal);
            compound.setInteger("duration", this.brew.duration);
            compound.setInteger("linger", this.brew.linger);
            compound.setInteger("power", this.brew.power);
            compound.setInteger("speed", this.brew.speed);
            compound.setInteger("type", this.brew.type);
            compound.setInteger("ticksRemaining", this.ticksRemaining);
            compound.setBoolean("non-null", true);

        } else {
            compound.setBoolean("non-null", false);
        }

    }

    public void setBrew(Brew newBrew){
        this.brew = newBrew;
    }

    public void setTicksRemaining(int ticksRemaining) {
        this.ticksRemaining = ticksRemaining;
    }

    public Brew getBrew() {
        return this.brew;
    }

}
