package herbarium.common.blocks.brewing;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public final class BlockCoil
extends Block {
  public BlockCoil() {
    super(Material.IRON);
  }

  @Override
  public boolean isNormalCube(IBlockState state) {
    return false;
  }

  @Override
  public boolean isOpaqueCube(IBlockState state) {
    return false;
  }
}