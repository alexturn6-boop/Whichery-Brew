package com.supersouper.whichery.common.BrewEffects.BrewType;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.supersouper.whichery.common.BrewEffects.BrewBlockTransformation;

public class BrewNetherize extends BrewBlockTransformation {

    public BrewNetherize(String id, int power, int duration, int speed, int dispersal, int linger, int type) {
        super(id, power, duration, speed, dispersal, linger, type);
    }

    @Override
    public boolean isBlock(World world, int x, int y, int z) {
        return (world.getBlock(x, y, z) == Blocks.stone);
    }

    @Override
    public void makeBlock(World world, int x, int y, int z) {
        world.setBlock(x, y, z, Blocks.netherrack);
    }

    @Override
    public void onEntity(EntityLivingBase entity) {

        entity.addPotionEffect(new PotionEffect(Potion.wither.id, duration, power));

        // if the entity is a skeleton, then it will make it a wither skeleton with a (1 - 1/(2*power)) chance
        if (entity.worldObj.rand.nextFloat() <= 0.5F) {
            if (entity.getClass() == EntitySkeleton.class && ((EntitySkeleton) entity).getSkeletonType() == 0) {
                ((EntitySkeleton) entity).setSkeletonType(1);
                entity.setPosition(entity.posX, entity.posY + 1.1F, entity.posZ);

            }
        }

    }
}
