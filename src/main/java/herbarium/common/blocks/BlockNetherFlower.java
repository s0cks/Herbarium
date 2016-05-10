package herbarium.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class BlockNetherFlower
extends BlockHerbariumFlower{

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        Block bottom = worldIn.getBlockState(pos.down())
                              .getBlock();
        return bottom == Blocks.SOUL_SAND;
    }
}