package com.supersouper.whichery.common.entity;

import com.supersouper.whichery.common.BrewEffects.Brew;
import com.supersouper.whichery.common.BrewEffects.BrewHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class EntityLingeringBrew extends Entity {

    public Brew brew;
    public int ticksRemaining = 0;
    public static int radius = 2; // does not count center blocks
    public static int heightRadius = 1; // does not count center blocks

    public EntityLingeringBrew(World p_i1788_1_) {

        super(p_i1788_1_);
        this.setSize(0F, 0F);
        this.setInvisible(true);

    }


    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(20, Integer.valueOf(0));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {

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
    public void writeEntityToNBT(NBTTagCompound compound) {

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
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (worldObj.isRemote) {
            spawnParticles();
        }
    }


    @Override
    public void onEntityUpdate(){
        super.onEntityUpdate();

        int currentX = (int)Math.floor(this.posX);
        int currentY = (int)Math.floor(this.posY);
        int currentZ = (int)Math.floor(this.posZ);


        if (brew != null && ticksRemaining % 20 == 0) {

            if (!worldObj.isRemote) {

                for (int x = currentX - radius; x <= currentX + radius; ++x) {
                    for (int z = currentZ - radius; z <= currentZ + radius; ++z) {
                        for (int y = currentY - heightRadius; y <= currentY + heightRadius; ++y) {
                            brew.onBlock(this.worldObj, x, y, z);
                        }
                    }
                }

            }


            AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(currentX - radius, currentY - heightRadius, currentZ - radius,
                currentX + radius, currentY + heightRadius, currentZ + radius);
            List<EntityLivingBase> list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
            Iterator<EntityLivingBase> iterator = list.iterator();
            EntityLivingBase entity;

            while (iterator.hasNext()) {
                entity = iterator.next();
                // call to entity effect
                brew.onEntity(entity);
            }

        }


        if (!worldObj.isRemote) {

            if (ticksRemaining <= 0){
                this.setDead();
            }
            ticksRemaining -= 1;
        }

    }

    public void setBrew(Brew newBrew){
        brew = newBrew;
        // this should be whatever value is being stored as the color
        this.dataWatcher.updateObject(20, brew.power);
    }

    public void setTicksRemaining(int newTicksRemaining) {
        ticksRemaining = newTicksRemaining;
    }

    public void spawnParticles() {
        int storedColor = this.dataWatcher.getWatchableObjectInt(20);
        double colorR = 0.5F;
        double colorG = 0.5F;
        double colorB = 0.5F;


        double offsetX = rand.nextFloat() * (double)radius * 2.0F - (double)radius;
        double offsetZ = rand.nextFloat() * (double)radius * 2.0F - (double)radius;

        if (storedColor == 1) {
            colorR = 1.0F;
            colorG = 0.0F;
            colorB = 0.0F;
        }

        this.worldObj.spawnParticle("mobSpell", posX + (offsetX), posY, posZ + (offsetZ), colorR, colorG, colorB);

    }

}
