package com.supersouper.whichery;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.supersouper.whichery.common.BrewEffects.BrewType.BrewFreeze;
import com.supersouper.whichery.common.BrewEffects.BrewType.BrewLaunch;
import com.supersouper.whichery.common.BrewEffects.BrewType.BrewNetherize;
import com.supersouper.whichery.common.items.ItemChalk;
import com.supersouper.whichery.common.items.ItemRawChickenThatMakesYouBecomeAVampire;
import com.supersouper.whichery.common.items.brews.ItemBrewGeneric;
import com.supersouper.whichery.common.items.seeds.ItemSeedFirePoppy;
import com.supersouper.whichery.common.items.seeds.ItemSeedMandrake;
import com.supersouper.whichery.common.items.seeds.ItemSeedSilphium;
import com.supersouper.whichery.common.items.seeds.ItemSeedWitchesThimble;

import cpw.mods.fml.common.registry.GameRegistry;

// Credit to Et Futurum (Requiem)
public enum ModItems {
    // spotless:off

    // make sure to leave a trailing comma
    CHALK(true, new ItemChalk(), "chalk"),
    WITCHES_THIMBLE_SEED(true, new ItemSeedWitchesThimble(), "seed_witches_thimble"),
    WITCH_PETAL(true, new Item().setTextureName("whichery:witch_petal").setUnlocalizedName("witch_petal"), "witch_petal"),
    MANDRAKE_SEED(true, new ItemSeedMandrake(), "seed_mandrake"),
    MANDRAKE_ROOT(true, new Item().setTextureName("whichery:mandrake_root").setUnlocalizedName("mandrake_root"), "mandrake_root"),
    RAW_CHICKEN_THAT_MAKES_YOU_BECOME_A_VAMPIRE(true, new ItemRawChickenThatMakesYouBecomeAVampire().setUnlocalizedName("raw_chicken_that_makes_you_become_a_vampire").setTextureName("chicken_raw"), "raw_chicken_that_makes_you_become_a_vampire"),
    SILPHIUM_SEED(true, new ItemSeedSilphium(), "seed_silphium"),
    SILPHIUM_FLOWER(true, new Item().setTextureName("whichery:silphium_flower").setUnlocalizedName("silphium_flower"), "silphium_flower"),
    SILPHIUM_WILTED_FLOWER(true, new Item().setTextureName("whichery:silphium_wilted_flower").setUnlocalizedName("silphium_wilted_flower"), "silphium_wilted_flower"),
    FIRE_POPPY_SEED(true, new ItemSeedFirePoppy(), "seed_fire_poppy"),
    TEST_BREW_ONE(true, new ItemBrewGeneric(new BrewFreeze("freeze",1,500,1,1,1,3)), "generic_brew_1"),
    TEST_BREW_TWO(true, new ItemBrewGeneric(new BrewNetherize("netherize",1,500,1,1,1,3)), "generic_brew_2"),
    TEST_BREW_THREE(true, new ItemBrewGeneric(new BrewLaunch("launch",1,500,1,1,1,3)), "generic_brew_3"),
    TEST_BREW_FOUR(true, new ItemBrewGeneric(new BrewFreeze("freeze",1,500,1,1,1,1)), "generic_brew_4"),
    TEST_BREW_FIVE(true, new ItemBrewGeneric(new BrewNetherize("netherize",1,500,1,1,1,1)), "generic_brew_5"),
    TEST_BREW_SIX(true, new ItemBrewGeneric(new BrewLaunch("launch",1,500,1,1,1,1)), "generic_brew_6"),
    ; // leave trailing semicolon
    // spotless:on

    public static final ModItems[] VALUES = values();

    public static void init() {
        for (ModItems item : VALUES) {
            if (item.isEnabled()) {
                item.theItem.setCreativeTab(Whichery.whicheryTab);
                GameRegistry.registerItem(item.get(), item.name);
            }
        }
    }

    private final boolean isEnabled;
    private final Item theItem;
    private final String name;

    ModItems(boolean enabled, Item item, String name) {
        this.isEnabled = enabled;
        theItem = item;
        this.name = name;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public Item get() {
        return theItem;
    }

    public ItemStack newItemStack() {
        return newItemStack(1);
    }

    public ItemStack newItemStack(int count) {
        return newItemStack(count, 0);
    }

    public ItemStack newItemStack(int count, int meta) {
        return new ItemStack(this.get(), count, meta);
    }
}
