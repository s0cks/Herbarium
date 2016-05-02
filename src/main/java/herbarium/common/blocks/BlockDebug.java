package herbarium.common.blocks;

import herbarium.api.HerbariumApi;
import herbarium.api.commentarium.IPage;
import herbarium.common.items.ItemPage;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public final class BlockDebug
extends Block {
    public BlockDebug(){
        super(Material.glass);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.getCurrentEquippedItem();
        if(stack == null) return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
        if(stack.getItem() instanceof ItemPage){
            IPage page = HerbariumApi.PAGE_TRACKER.unlearnedPage(playerIn);
            if(page == null) return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
            ItemPage.setPage(stack, page);
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
    }
}