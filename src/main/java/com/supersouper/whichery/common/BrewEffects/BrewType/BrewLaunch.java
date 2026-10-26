package com.supersouper.whichery.common.BrewEffects.BrewType;

import com.supersouper.whichery.common.BrewEffects.Brew;
import net.minecraft.entity.EntityLivingBase;


public class BrewLaunch extends Brew {

    public BrewLaunch(String id, int power, int duration, int speed, int dispersal, int linger, int type) {
        super(id, power, duration, speed, dispersal, linger, type);
    }

    @Override
    public void onEntity(EntityLivingBase entity){
        entity.setVelocity(entity.motionX,entity.motionY + (0.75F * (power + 1)),entity.motionZ );
    }
}
