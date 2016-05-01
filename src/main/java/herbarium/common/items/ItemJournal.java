package herbarium.common.items;

import herbarium.api.HerbariumApi;
import herbarium.api.commentarium.IPageLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public final class ItemJournal
extends Item {
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        IPageLocation loc = HerbariumApi.PAGE_MANAGER.generator().nextLocation(worldIn, pos);
        if(loc != null){
            playerIn.addChatComponentMessage(new ChatComponentText(String.format("New Page [%s] @%s", loc.page().uuid(), loc.exactLocation().toString())));
        }
        return true;
    }
}