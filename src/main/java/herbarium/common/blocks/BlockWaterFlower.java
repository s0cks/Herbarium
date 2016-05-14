package herbarium.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public final class BlockWaterFlower
    extends BlockHerbariumFlower {
  @Override
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    Block bottom = worldIn.getBlockState(pos.down())
                          .getBlock();
    return bottom == Blocks.WATER;
  }

  public static final class SpringLotusColorizer
      implements IBlockColor {
    @Override
    public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
      if (tintIndex == 0) {
        boolean inWorld = worldIn != null && pos != null;
        return inWorld ?
                   2129968 :
                   7455580;
      }
      return 0xFFFFFF;
    }
  }
}