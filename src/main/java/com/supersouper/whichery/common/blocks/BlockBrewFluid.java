package com.supersouper.whichery.common.blocks;

import java.util.Random;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

import com.supersouper.whichery.ModBlocks;
import com.supersouper.whichery.common.BrewEffects.Brew;
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

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        // using BlockFluidClassic as a reference and updating the tile entities for blocks being flowed into
        LiquidTileEntity tileEntity = (LiquidTileEntity) world.getTileEntity(x, y, z);

        if (tileEntity != null && tileEntity.getBrew() != null) {

            // this needed to be canFlowInto for some reason instead of canDisplace
            if (canFlowInto(world, x, y + densityDir, z)) {
                flowIntoBlock(world, x, y + densityDir, z, 1);
                world.setTileEntity(
                    x,
                    y + densityDir,
                    z,
                    new LiquidTileEntity(tileEntity.getBrew(), tileEntity.getTicksRemaining()));
                return;
            }

            if (isSourceBlock(world, x, y, z) || !isFlowingVertically(world, x, y, z)) {

                boolean flowTo[] = getOptimalFlowDirections(world, x, y, z);

                if (flowTo[0])
                    updateTileEntity(world, x - 1, y, z, tileEntity.getBrew(), tileEntity.getTicksRemaining());
                if (flowTo[1])
                    updateTileEntity(world, x + 1, y, z, tileEntity.getBrew(), tileEntity.getTicksRemaining());
                if (flowTo[2])
                    updateTileEntity(world, x, y, z - 1, tileEntity.getBrew(), tileEntity.getTicksRemaining());
                if (flowTo[3])
                    updateTileEntity(world, x, y, z + 1, tileEntity.getBrew(), tileEntity.getTicksRemaining());
            }
        }

        super.updateTick(world, x, y, z, rand);
    }

    // replaces the tile entity at a given position with one with an updated brew
    private void updateTileEntity(World world, int x, int y, int z, Brew brew, int ticksRemaining) {
        if (world.getBlock(x, y, z) == ModBlocks.BREW_FLUID.get()) {
            LiquidTileEntity tileEntity = (LiquidTileEntity) world.getTileEntity(x, y, z);
            if (tileEntity == null || tileEntity.getBrew() == null) {
                world.removeTileEntity(x, y, z);
                world.setTileEntity(x, y, z, new LiquidTileEntity(brew, ticksRemaining));
            }

        }
    }

}
