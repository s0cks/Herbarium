package herbarium.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHerbariumFlower
    extends Block {
  private final AxisAlignedBB box = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.75, 0.75);

  public BlockHerbariumFlower() {
    super(Material.PLANTS);
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
  public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
    return null;
  }

  @Override
  public boolean isOpaqueCube(IBlockState state) {
    return false;
  }

  @Override
  public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
    if (!canPlaceBlockAt(worldIn, pos)) {
      worldIn.destroyBlock(pos, true);
    }
  }

  @Override
  public BlockRenderLayer getBlockLayer() {
    return BlockRenderLayer.CUTOUT;
  }

  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    Block bottom = worldIn.getBlockState(pos.down())
                          .getBlock();
    return bottom == Blocks.DIRT
               || bottom == Blocks.GRASS;
  }
}
