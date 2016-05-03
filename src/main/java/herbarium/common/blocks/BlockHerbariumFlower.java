package herbarium.common.blocks;

import herbarium.api.IFlower;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

public class BlockHerbariumFlower
extends Block{
    public final IFlower flower;

    public BlockHerbariumFlower(IFlower flower){
        super(Material.plants);
        this.setStepSound(Block.soundTypeGrass);
        this.flower = flower;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        Block bottom = worldIn.getBlockState(pos.down()).getBlock();
        return bottom == Blocks.dirt
            || bottom == Blocks.grass;
    }

    @Override
    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
        if(!canPlaceBlockAt(worldIn, pos)){
            worldIn.destroyBlock(pos, true);
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
        return null;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isNormalCube() {
        return false;
    }

    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
}