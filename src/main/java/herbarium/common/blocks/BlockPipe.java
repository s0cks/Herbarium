package herbarium.common.blocks;

import herbarium.common.tiles.TileEntityPipe;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public final class BlockPipe
extends BlockContainer{
    public BlockPipe(){
        super(Material.IRON);
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
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
        for(EnumFacing dir : EnumFacing.values()){
            TileEntity other = worldIn.getTileEntity(pos.offset(dir));
            if(other instanceof TileEntityPipe){
                ((TileEntityPipe) other).connector().remove(pos);
            }
        }
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        TileEntityPipe self = ((TileEntityPipe) worldIn.getTileEntity(pos));
        for(EnumFacing dir : EnumFacing.values()){
            TileEntity other = worldIn.getTileEntity(pos.offset(dir));
            if(other instanceof TileEntityPipe){
                self.connector().add(pos.offset(dir));
            }
        }
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        TileEntityPipe self = ((TileEntityPipe) world.getTileEntity(pos));
        if(world.getTileEntity(neighbor) instanceof TileEntityPipe){
            TileEntityPipe other = ((TileEntityPipe) world.getTileEntity(neighbor));
            if(!other.connector().hasConnectionTo(pos)){
                other.connector().add(pos);
                self.connector().add(other.getPos());
            }
        }

        if(self.connector().hasConnectionTo(neighbor) && world.isAirBlock(neighbor)){
            self.connector().remove(neighbor);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityPipe();
    }
}