package herbarium.common.blocks;

import herbarium.common.core.Flowers;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class BlockWaterFlower
extends BlockHerbariumFlower{
    public BlockWaterFlower(){
        super(Flowers.NETHER);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        Block bottom = worldIn.getBlockState(pos.down()).getBlock();
        return bottom == Blocks.WATER;
    }
}