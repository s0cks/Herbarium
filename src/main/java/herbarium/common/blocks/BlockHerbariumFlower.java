package herbarium.common.blocks;

import herbarium.api.botany.IFlower;
import herbarium.common.core.botany.Flower;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.List;

public class BlockHerbariumFlower
extends Block {
  protected final IFlower flower;
  protected final ThreadLocal<List<ItemStack>> drops = new ThreadLocal<>();

  private final AxisAlignedBB box = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.75, 0.75);

  public BlockHerbariumFlower(IFlower flower) {
    super(Material.PLANTS);
    this.flower = flower;
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

  @Override
  public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
    ItemStack flowerStack = new ItemStack(this, 1);

    NBTTagCompound comp = new NBTTagCompound();
    this.flower.writeToNBT(comp);
    flowerStack.setTagCompound(comp);

    List<ItemStack> result = new LinkedList<>();
    result.add(flowerStack);
    this.drops.set(result);
  }

  @Override
  public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    List<ItemStack> result = this.drops.get();
    this.drops.set(null);
    return result;
  }

  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
    if (!stack.hasTagCompound()) return;
    IFlower flower = new Flower(stack.getTagCompound());
    tooltip.add("Primary: " + flower.genome()
                                    .primary()
                                    .uuid());
    tooltip.add("Secondary: " + flower.genome()
                                      .secondary()
                                      .uuid());
  }
}
