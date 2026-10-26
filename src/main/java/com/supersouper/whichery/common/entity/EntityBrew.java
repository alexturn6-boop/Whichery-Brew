package com.supersouper.whichery.common.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.supersouper.whichery.ModBlocks;
import com.supersouper.whichery.common.BrewEffects.Brew;
import com.supersouper.whichery.common.tileentities.LiquidTileEntity;

public class EntityBrew extends EntityThrowable {

    // brew type
    private Brew brew;

    public EntityBrew(World world) {
        super(world);
    }

    public EntityBrew(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public EntityBrew(World world, EntityLivingBase player) {
        super(world, player);
    }

    public void setBrew(Brew newBrew) {
        this.brew = newBrew;
    }

    // Setting gravity and such to be similar to XP bottles for now
    @Override
    protected float getGravityVelocity() {
        return 0.07F;
    }

    @Override
    protected float func_70182_d() {
        return 0.7F;
    }

    @Override
    protected float func_70183_g() {
        return -20.0F;
    }

    @Override
    protected void onImpact(MovingObjectPosition movingObjectPosition) {
        //
        int currentX = (int) Math.floor(this.posX);
        int currentY = (int) Math.floor(this.posY);
        int currentZ = (int) Math.floor(this.posZ);

        if (brew != null && !this.worldObj.isRemote) {
            // on impact effect will go off no matter what type it is

            this.worldObj.playAuxSFX(2002, currentX, currentY, currentZ, 1);

            brew.onImpact(worldObj, currentX, currentY, currentZ);

            if (brew.type == 1) {
                // handles case for splash potions/ lingering potions

                EntityLingeringBrew lingeringBrew = new EntityLingeringBrew(this.worldObj);
                lingeringBrew.setPosition(posX, posY, posZ);

                this.worldObj.spawnEntityInWorld(lingeringBrew);
                lingeringBrew.setBrew(brew);
                lingeringBrew.setTicksRemaining(brew.linger * 400);
                this.setDead();
            } else if (brew.type == 2) {
                // handles case for gas potions

                this.setDead();
            } else if (brew.type == 3) {
                // handles case for liquid potions
                if (this.worldObj.getBlock(currentX, currentY, currentZ)
                    .isReplaceable(worldObj, currentX, currentY, currentZ)
                    || this.worldObj.getBlock(currentX, currentY, currentZ) == Blocks.air) {

                    this.worldObj.setBlock(currentX, currentY, currentZ, ModBlocks.BREW_FLUID.get());

                    TileEntity liquidTileEntity = new LiquidTileEntity();

                    NBTTagCompound nbtData = new NBTTagCompound();

                    nbtData.setString("brew-id", this.brew.id);
                    nbtData.setInteger("dispersal", this.brew.dispersal);
                    nbtData.setInteger("duration", this.brew.duration);
                    nbtData.setInteger("linger", this.brew.linger);
                    nbtData.setInteger("power", this.brew.power);
                    nbtData.setInteger("speed", this.brew.speed);
                    nbtData.setInteger("type", this.brew.type);
                    nbtData.setInteger("ticksRemaining", (brew.linger + 1) * 300);

                    liquidTileEntity.readFromNBT(nbtData);
                    this.worldObj.removeTileEntity(currentX, currentY, currentZ);
                    this.worldObj.setTileEntity(currentX, currentY, currentZ, liquidTileEntity);
                }

                this.setDead();
            } else if (brew.type == 4) {
                // handles case for trigger potions (if we are going to use them)

                // A trigger potion is supposed to turn an object into something where when interacted with will
                // activate the potion
                // like a door or a pressure plate or something

                // leaving blank for now
                this.setDead();
            } else if (brew.type == 5) {
                // handles any other case

                // leaving blank for now
                this.setDead();
            } else {
                this.setDead();
            }
        }
    }
}
