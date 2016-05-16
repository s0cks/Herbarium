package herbarium.common.blocks;

import herbarium.api.brew.IBrew;
import herbarium.api.brew.piping.IBrewStack;
import herbarium.common.items.ItemBrew;
import herbarium.common.tiles.TileEntityBrewBarrel;
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

public final class BlockBarrel
extends BlockContainer {
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

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
    ItemStack stack = playerIn.getHeldItem(EnumHand.MAIN_HAND);
    TileEntityBrewBarrel barrel = ((TileEntityBrewBarrel) worldIn.getTileEntity(pos));
    if (stack == null) {
      playerIn.addChatComponentMessage(new TextComponentString("Suction: " + barrel.suction(EnumFacing.UP)));
      playerIn.addChatComponentMessage(new TextComponentString("Amount: " + barrel.amount()));
      return true;
    } else{
      if (stack.getItem() instanceof ItemBrew){
        IBrew brew = ItemBrew.getBrew(stack);
        if(brew != null){
          barrel.add(brew, IBrewStack.MAX_AMOUNT, EnumFacing.UP);
        }
      }
    }
    return false;
  }

  @Override
  public EnumBlockRenderType getRenderType(IBlockState state) {
    return EnumBlockRenderType.MODEL;
  }

  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileEntityBrewBarrel();
  }
}