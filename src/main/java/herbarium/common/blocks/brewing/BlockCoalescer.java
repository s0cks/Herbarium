package herbarium.common.blocks.brewing;

import herbarium.api.brew.IBrew;
import herbarium.api.brew.effects.IEffect;
import herbarium.common.tiles.brewing.TileEntityCoalescer;
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
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public final class BlockCoalescer
extends BlockContainer {
  public BlockCoalescer() {
    super(Material.IRON);
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
    ItemStack stack = playerIn.getHeldItem(EnumHand.MAIN_HAND);
    TileEntityCoalescer coalescer = (((TileEntityCoalescer) worldIn.getTileEntity(pos)));
    if (stack == null) {
      playerIn.addChatComponentMessage(new TextComponentString("Suction: " + coalescer.suction(side)));
      playerIn.addChatComponentMessage(new TextComponentString("Amount: " + (coalescer.getInput(side) != null
                                                                             ? coalescer.getInput(side)
                                                                                        .amount()
                                                                             : 0)));

      if (coalescer.getInput(side) != null) {
        IBrew brew = coalescer.getInput(side)
                              .brew();
        for (IEffect effect : brew.effects()) {
          playerIn.addChatComponentMessage(new TextComponentString(I18n.translateToLocal(effect.uuid())));
        }
      }
      return true;
    }
    return false;
  }

  @Override
  public EnumBlockRenderType getRenderType(IBlockState state) {
    return EnumBlockRenderType.MODEL;
  }

  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileEntityCoalescer();
  }
}