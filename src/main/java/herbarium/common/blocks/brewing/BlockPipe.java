package herbarium.common.blocks.brewing;

import herbarium.common.tiles.brewing.TileEntityPipe;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public final class BlockPipe
extends BlockContainer {
  public BlockPipe() {
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

  @Override
  public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
  }

  @Override
  public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
    ItemStack stack = playerIn.getHeldItem(EnumHand.MAIN_HAND);
    if (stack == null) {
      TileEntityPipe pipe = ((TileEntityPipe) worldIn.getTileEntity(pos));
      playerIn.addChatComponentMessage(new TextComponentString("Suction: " + pipe.suction(null)));
      return true;
    }
    return false;
  }

  @Override
  public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
  }

  public EnumBlockRenderType getRenderType(IBlockState state) {
    return EnumBlockRenderType.INVISIBLE;
  }

  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileEntityPipe();
  }
}