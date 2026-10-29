package com.supersouper.whichery.common.tileentities;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import com.supersouper.whichery.ModBlocks;
import com.supersouper.whichery.common.BrewEffects.Brew;
import com.supersouper.whichery.common.BrewEffects.BrewHelper;

public class LiquidTileEntity extends TileEntity {

    public Brew brew;
    public int ticksRemaining = 100;

    public LiquidTileEntity(Brew brew, int ticksRemaining){
        this.brew = brew;
        this.ticksRemaining = ticksRemaining;
    }
    public LiquidTileEntity(){
        super();
    }

    @Override
    public void updateEntity() {
        int x = this.xCoord;
        int y = this.yCoord;
        int z = this.zCoord;

        if (brew != null) {

            if (ticksRemaining % 20 == 0) {

                // handles effects on entities
                AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
                List<EntityLivingBase> list = this.worldObj
                    .getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
                Iterator<EntityLivingBase> iterator = list.iterator();
                EntityLivingBase entity;
                while (iterator.hasNext()) {
                    entity = iterator.next();
                    // call to entity effect
                    brew.onEntity(entity);
                }

                // code to affect adjacent blocks
                brew.onBlock(this.worldObj, x + 1, y, z);
                brew.onBlock(this.worldObj, x - 1, y, z);
                brew.onBlock(this.worldObj, x, y + 1, z);
                brew.onBlock(this.worldObj, x, y - 1, z);
                brew.onBlock(this.worldObj, x, y, z + 1);
                brew.onBlock(this.worldObj, x, y, z - 1);

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
                compound.getInteger("type"));
            this.ticksRemaining = compound.getInteger("ticksRemaining");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {

        super.writeToNBT(compound);

        if (this.brew != null) {
            compound.setString("brew-id", this.brew.id);
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

    public void setBrew(Brew newBrew) {
        this.brew = newBrew;
    }

    public void setTicksRemaining(int ticksRemaining) {
        this.ticksRemaining = ticksRemaining;
    }

    public Brew getBrew() {
        return this.brew;
    }

    public int getTicksRemaining() {return  this.ticksRemaining;}
    @Override
    public boolean canUpdate(){
        return this.brew != null && !Objects.equals(this.brew.id, "color");
    }

}
