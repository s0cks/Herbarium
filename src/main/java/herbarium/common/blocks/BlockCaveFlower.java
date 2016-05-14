package herbarium.common.blocks;

import herbarium.api.botany.IFlower;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class BlockCaveFlower
    extends BlockHerbariumFlower {
  public BlockCaveFlower(IFlower flower){
    super(flower);
  }

  @Override
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    Block top = worldIn.getBlockState(pos.up())
                       .getBlock();
    return top == Blocks.STONE;
  }
}