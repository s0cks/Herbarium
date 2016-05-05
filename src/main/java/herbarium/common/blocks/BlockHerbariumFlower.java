package herbarium.common.blocks;

import herbarium.api.IFlower;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockHerbariumFlower
extends Block{
    public final IFlower flower;

    public BlockHerbariumFlower(IFlower flower){
        super(Material.PLANTS);
        this.setSoundType(SoundType.GROUND);
        this.flower = flower;
    }

    @Override
    public boolean isNormalCube(IBlockState state) {
        return false;
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        Block bottom = worldIn.getBlockState(pos.down()).getBlock();
        return bottom == Blocks.DIRT
            || bottom == Blocks.GRASS;
    }

    @Override
    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
        if(!canPlaceBlockAt(worldIn, pos)){
            worldIn.destroyBlock(pos, true);
        }
    }

    /*
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        Block bottom = worldIn.getBlockState(pos.down()).getBlock();
        return bottom == Blocks.DIRT
            || bottom == Blocks.GRASS;
    }

    @Override
    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
        if(!canPlaceBlockAt(worldIn, pos)){
            worldIn.destroyBlock(pos, true);
        }
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }
    */
}
