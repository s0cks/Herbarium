package herbarium.common.items;

import herbarium.common.Herbarium;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class ItemJournal
extends Item {
    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!playerIn.isSneaking()){
            playerIn.openGui(Herbarium.instance, Herbarium.GUI_JOURNAL, worldIn, ((int) playerIn.posX), ((int) playerIn.posY), ((int) playerIn.posZ));
        } else{
            if(worldIn.isAirBlock(pos.up())){
                stack.stackSize--;
                worldIn.setBlockState(pos.up(), Herbarium.blockJournal.getDefaultState());
            }
        }
        return EnumActionResult.SUCCESS;
    }
}