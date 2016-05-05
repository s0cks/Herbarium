package herbarium.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public final class BlockCoil
        extends Block{
    public BlockCoil(){
        super(Material.iron);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isNormalCube() {
        return false;
    }
}