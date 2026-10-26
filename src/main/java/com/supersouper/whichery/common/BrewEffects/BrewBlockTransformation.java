package com.supersouper.whichery.common.BrewEffects;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BrewBlockTransformation extends Brew {

    // Generalized Brew for turning blocks into other blocks

    public BrewBlockTransformation(String id, int power, int duration, int speed, int dispersal, int linger, int type) {
        super(id, power, duration, speed, dispersal, linger, type);
    }

    public boolean isBlock(World world, int x, int y, int z) {
        return (world.getBlock(x, y, z) == Blocks.air);
    }

    public void makeBlock(World world, int x, int y, int z) {
        world.setBlock(x, y, z, Blocks.air);
    }

    public void onBlock(World world, int x, int y, int z) {

        if (world.rand.nextFloat() * power >= 0.5F) {
            if (isBlock(world, x, y, z)) {
                makeBlock(world, x, y, z);
            }
        }

    }

}
