package com.supersouper.whichery.common.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

import com.supersouper.whichery.common.tileentities.LiquidTileEntity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBrewFluid extends BlockFluidClassic implements ITileEntityProvider {

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public BlockBrewFluid(Fluid fluid, Material material) {
        super(fluid, material);
        setBlockName("brew_fluid");
    }

    @Override
    public int getBlockColor() {
        return 1;
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new LiquidTileEntity();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        icons = new IIcon[2];
        icons[0] = reg.registerIcon("whichery:" + "brew_fluid");
        icons[1] = reg.registerIcon("whichery:" + "brew_flow");

    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (meta == 0) {
            return icons[0];
        } else {
            return icons[1];
        }
    }

}
