package com.supersouper.whichery.common.items.brews;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.supersouper.whichery.common.BrewEffects.Brew;
import com.supersouper.whichery.common.entity.EntityBrew;

public class ItemBrewGeneric extends Item {

    public Brew brew;

    public ItemBrewGeneric(Brew brew) {
        this.brew = brew;

        setUnlocalizedName("brew");
    }

    public ItemStack onItemRightClick(ItemStack itemStackIn, World world, EntityPlayer player) {
        if (brew != null && brew.type != 0) {

            if (!player.capabilities.isCreativeMode) {
                --itemStackIn.stackSize;
            }

            world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

            if (!world.isRemote) {
                EntityBrew brewEntity = new EntityBrew(world, player);
                brewEntity.setBrew(brew);
                world.spawnEntityInWorld(brewEntity);
            }

        }
        return itemStackIn;
    }

    @Override
    public void addInformation(ItemStack itemStackIn, EntityPlayer player, List<String> list, boolean par4) {
        // not sure what the bool is for but this should be adding extra info to the brews
        if (brew != null) {
            list.add(this.brew.id + ": (" + (brew.duration / (20 * 60)) + ":" + (brew.duration / (20) % 60) + ")");

            switch (this.brew.type) {
                case 0:
                    return;
                case 1:
                    list.add("Splash");
                    break;
                case 2:
                    list.add("Gas");
                    break;
                case 3:
                    list.add("Liquid");
                    break;
                default:
                    break;

            }

        }
    }
}
