package com.supersouper.whichery.common.BrewEffects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class Brew {
    // Generic format of the brew event

    // brew type
    public String id;

    // power level of potion
    public int power;

    // level of duration for effects
    public int duration;

    // Speed is for drinking only
    public int speed;

    // power level for dispersal
    public int dispersal;

    // power level for lingering
    public int linger;

    // type = 1 = splash/lingering, type = 2 = gas, type = 3 = liquid, type = 4 = trigger
    public int type;

    public Brew(String id, int power, int duration, int speed, int dispersal, int linger, int type) {
        this.id = id;
        this.power = power;
        this.duration = duration;
        this.speed = speed;
        this.dispersal = dispersal;
        this.linger = linger;
        this.type = type;
    }

    // handles effects for onBlock effects, only brew type block location and power level are needed
    public void onBlock(World world, int x, int y, int z) {

    }

    // handles effects for onEntity effects, only brew type entity and duration are needed
    public void onEntity(EntityLivingBase entity) {

    }

    // for special cases where the inital impact is the only thing that matters (only for splash)
    public void onImpact(World world, int x, int y, int z) {

    }

    public void updatePower(int newValue) {
        this.power = newValue;
    }

    public void updateDuration(int newValue) {
        this.duration = newValue;
    }

    public void updateSpeed(int newValue) {
        this.speed = newValue;
    }

    public void updateDispersal(int newValue) {
        this.dispersal = newValue;
    }

    public void updateLinger(int newValue) {
        this.linger = newValue;
    }

    public void updateType(int newValue) {
        this.type = newValue;
    }
}
