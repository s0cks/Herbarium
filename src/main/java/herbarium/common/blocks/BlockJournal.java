package herbarium.common.blocks;

import herbarium.common.Herbarium;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.Random;

public final class BlockJournal
extends Block {
  private final AxisAlignedBB box = new AxisAlignedBB(0.275, 0.0, 0.275, 0.725, 0.2, 0.725);

  public BlockJournal() {
    super(Material.CLOTH);
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

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return Herbarium.itemJournal;
  }
}