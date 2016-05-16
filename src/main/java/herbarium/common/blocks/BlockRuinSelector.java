package herbarium.common.blocks;

import herbarium.common.tiles.TileEntityRuinSelector;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class BlockRuinSelector
extends BlockContainer {
  public BlockRuinSelector(){
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
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
    ItemStack stack = playerIn.getHeldItem(EnumHand.MAIN_HAND);
    if(stack == null){
      TileEntityRuinSelector selector = ((TileEntityRuinSelector) worldIn.getTileEntity(pos));
      if(playerIn.isSneaking()){
        selector.contract();
      } else{
        selector.expand();
      }
      worldIn.markBlockRangeForRenderUpdate(pos, pos);
      return true;
    }

    return false;
  }

  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileEntityRuinSelector();
  }
}