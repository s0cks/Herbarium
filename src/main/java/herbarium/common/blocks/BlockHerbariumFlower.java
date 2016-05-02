package herbarium.common.blocks;

import herbarium.api.IFlower;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumWorldBlockLayer;

public final class BlockHerbariumFlower
extends Block{
    public final IFlower flower;

    public BlockHerbariumFlower(IFlower flower){
        super(Material.plants);
        this.flower = flower;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isNormalCube() {
        return false;
    }

    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
}