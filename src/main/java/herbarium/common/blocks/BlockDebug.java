package herbarium.common.blocks;

import herbarium.api.HerbariumApi;
import herbarium.common.items.ItemPage;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class BlockDebug
    extends Block {
  public BlockDebug() {
    super(Material.GLASS);
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
    if (playerIn.getHeldItem(EnumHand.MAIN_HAND) != null) {
      ItemStack stack = playerIn.getHeldItem(EnumHand.MAIN_HAND);
      if (stack.getItem() instanceof ItemPage) {
        ItemPage.setPage(stack, HerbariumApi.PAGE_TRACKER.unlearnedPage(playerIn));
      }
    }
    return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
  }
}