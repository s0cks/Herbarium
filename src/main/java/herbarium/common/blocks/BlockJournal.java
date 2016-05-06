package herbarium.common.blocks;

import herbarium.common.Herbarium;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class BlockJournal
        extends BlockFalling{
    public BlockJournal(){
        super(Material.CLOTH);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state) {
        return false;
    }

    @Override
    public void onEndFalling(World worldIn, BlockPos pos) {
        worldIn.setBlockState(pos, Herbarium.blockJournal.getDefaultState());
    }
}