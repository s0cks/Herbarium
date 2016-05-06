package herbarium.common.blocks;

import herbarium.common.core.Flowers;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class BlockWaterFlower
        extends BlockHerbariumFlower{
    public BlockWaterFlower(){
        super(Flowers.LOTUS);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        Block top = worldIn.getBlockState(pos.up()).getBlock();
        return top == Blocks.WATER;
    }
}