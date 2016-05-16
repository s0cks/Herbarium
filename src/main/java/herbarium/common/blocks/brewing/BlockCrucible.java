package herbarium.common.blocks.brewing;

import herbarium.api.brew.IBrew;
import herbarium.api.brew.piping.IBrewStack;
import herbarium.common.core.brew.piping.BrewStack;
import herbarium.common.items.ItemBrew;
import herbarium.common.tiles.TileEntityCrucible;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public final class BlockCrucible
extends BlockContainer {
  private final AxisAlignedBB box = new AxisAlignedBB(0.1, 0.0, 0.1, 0.9, 0.875, 0.9);

  public BlockCrucible() {
    super(Material.IRON);
  }

  @Override
  public EnumBlockRenderType getRenderType(IBlockState state) {
    return EnumBlockRenderType.MODEL;
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
    TileEntityCrucible crucible = ((TileEntityCrucible) worldIn.getTileEntity(pos));
    if (stack == null) {
      playerIn.addChatComponentMessage(new TextComponentString("Suction: " + crucible.suction(EnumFacing.UP)));
      playerIn.addChatComponentMessage(new TextComponentString("Amount: " + crucible.amount()));
      return true;
    } else {
      if (stack.getItem() instanceof ItemBrew) {
        IBrew brew = ItemBrew.getBrew(stack);
        if (brew != null) {
          crucible.setBrewStack(new BrewStack(brew, IBrewStack.MAX_AMOUNT));
        }
      }
    }
    return false;
  }

  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileEntityCrucible();
  }
}