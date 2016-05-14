package herbarium.common.blocks;

import herbarium.api.botany.IFlower;
import herbarium.api.genetics.IChromosome;
import herbarium.common.core.botany.Flower;
import herbarium.common.tiles.TileEntityFlower;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.List;

public class BlockHerbariumFlower
    extends BlockContainer{
  protected final IFlower flower;
  protected final ThreadLocal<List<ItemStack>> drops = new ThreadLocal<>();

  private final AxisAlignedBB box = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.75, 0.75);

  public BlockHerbariumFlower(IFlower flower) {
    super(Material.PLANTS);
    this.flower = flower;
    for(IChromosome chromosome : this.flower.genome().chromosomes()){
      if(chromosome != null){
        System.out.println(chromosome.active().uuid());
        System.out.println(chromosome.inactive().uuid());
      } else{
        System.out.println("Null");
      }
    }
  }

  @Override
  public boolean isNormalCube(IBlockState state) {
    return false;
  }

  @Override
  public EnumBlockRenderType getRenderType(IBlockState state) {
    return EnumBlockRenderType.MODEL;
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
  public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
    if(!stack.hasTagCompound()) return;
    IFlower flower = new Flower(stack.getTagCompound());
    tooltip.add("Primary: " + flower.genome().primary().uuid());
    tooltip.add("Secondary: " + flower.genome().secondary().uuid());
  }

  @Override
  public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
    ItemStack flowerStack = new ItemStack(this, 1);

    IFlower flower = (((TileEntityFlower) worldIn.getTileEntity(pos)).flower());

    NBTTagCompound comp = new NBTTagCompound();
    flower.writeToNBT(comp);
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
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileEntityFlower(this.flower);
  }
}
