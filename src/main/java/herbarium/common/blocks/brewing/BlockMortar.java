package herbarium.common.blocks.brewing;

import herbarium.api.IPestle;
import herbarium.common.blocks.flowers.BlockHerbariumFlower;
import herbarium.common.tiles.TileEntityMortar;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public final class BlockMortar
extends BlockContainer {
  private final AxisAlignedBB box = new AxisAlignedBB(0.15, 0.0, 0.15, 0.85, 0.45, 0.85);

  public BlockMortar() {
    super(Material.ROCK);
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
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
    ItemStack stack = playerIn.getHeldItem(EnumHand.MAIN_HAND);
    TileEntityMortar mortar = ((TileEntityMortar) worldIn.getTileEntity(pos));
    if (stack == null && playerIn.isSneaking()) {
      //TODO: Drop mortar plants
      return false;
    } else if (stack == null) {
      return false;
    }

    if (stack.getItem() instanceof ItemBlock) {
      ItemBlock ib = ((ItemBlock) stack.getItem());
      if (ib.getBlock() instanceof BlockHerbariumFlower) {
        mortar.setCurrentItem(stack);
        mortar.update();
        playerIn.inventory.decrStackSize(playerIn.inventory.currentItem, stack.stackSize);
        return true;
      }
    } else if (stack.getItem() instanceof IPestle) {
      mortar.mush();
      mortar.update();
      return true;
    }

    return false;
  }

  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileEntityMortar();
  }
}