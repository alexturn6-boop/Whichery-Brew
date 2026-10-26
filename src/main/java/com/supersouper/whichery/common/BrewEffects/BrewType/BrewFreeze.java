package com.supersouper.whichery.common.BrewEffects.BrewType;

import com.supersouper.whichery.common.BrewEffects.BrewBlockTransformation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;


public class BrewFreeze extends BrewBlockTransformation {



    public BrewFreeze(String id, int power, int duration, int speed, int dispersal, int linger, int type) {
        super(id, power, duration, speed, dispersal, linger, type);
    }
    @Override
    public boolean isBlock(World world, int x, int y, int z) {
        return (world.getBlock(x, y, z) == Blocks.snow);
    }

    @Override
    public void makeBlock(World world, int x, int y, int z) {
        world.setBlock(x, y, z, Blocks.ice);
    }

    @Override
    public void onEntity(EntityLivingBase entity){
        entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id,duration,power));
    }
}
