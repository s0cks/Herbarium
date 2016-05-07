package herbarium.common.blocks;

import herbarium.api.commentarium.IPage;
import herbarium.common.core.commentarium.PageBuilder;
import herbarium.common.core.commentarium.PageGroups;
import herbarium.common.core.commentarium.renderer.MarkdownPageRenderer;
import herbarium.common.items.ItemPage;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class BlockDebug
extends Block {
    public BlockDebug(){
        super(Material.GLASS);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(playerIn.getHeldItem(hand) != null && playerIn.getHeldItem(hand).getItem() instanceof ItemPage){
            IPage page = ItemPage.getPage(playerIn.getHeldItem(hand));
            if(page == null){
                ItemPage.setPage(playerIn.getHeldItem(hand), new PageBuilder()
                    .setGroup(PageGroups.BLOCKS)
                    .setRenderer(new MarkdownPageRenderer(new ResourceLocation("herbarium", "pages/TropicalBerries.md"))).setTitle("Tropical Berries").build());
            }
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
    }
}