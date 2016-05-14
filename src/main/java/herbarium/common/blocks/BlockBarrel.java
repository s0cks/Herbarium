package herbarium.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public final class BlockBarrel
    extends Block {
  private final AxisAlignedBB box = new AxisAlignedBB(0.1, 0.0, 0.1, 0.9, 0.95, 0.9);

  public BlockBarrel() {
    super(Material.WOOD);
  }

  @Override
  public boolean isNormalCube(IBlockState state) {
    return false;
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return this.box;
  }

  @Override
  public boolean isOpaqueCube(IBlockState state) {
    return false;
  }
}