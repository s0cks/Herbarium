package herbarium.common.blocks;

import herbarium.api.IFlower;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockHerbariumFlower
extends Block{
    public final IFlower flower;

    public BlockHerbariumFlower(IFlower flower){
        super(Material.PLANTS);
        this.blockSoundType = SoundType.PLANT;
        this.flower = flower;
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

    /*
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
    */
}
