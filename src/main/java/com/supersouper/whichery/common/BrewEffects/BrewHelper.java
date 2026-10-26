package com.supersouper.whichery.common.BrewEffects;

import com.supersouper.whichery.common.BrewEffects.BrewType.BrewLaunch;
import com.supersouper.whichery.common.BrewEffects.BrewType.BrewNetherize;


import com.supersouper.whichery.common.BrewEffects.BrewType.BrewFreeze;

public class BrewHelper {

    // not sure the best way to do this, but for now, to add a new brew add a case followed by its id string
    // followed by the creation of the new brew class

    public static Brew createBrewFromID(String id, int power, int duration, int speed, int dispersal, int linger, int type) {

        return switch (id) {
            case "freeze" -> new BrewFreeze(id, power, duration, speed, dispersal, linger, type);
            case "netherize" -> new BrewNetherize(id, power, duration, speed, dispersal, linger, type);
            case "launch" -> new BrewLaunch(id, power, duration, speed, dispersal, linger, type);
            default -> new Brew(id, power, duration, speed, dispersal, linger, type);
        };
    }
}
