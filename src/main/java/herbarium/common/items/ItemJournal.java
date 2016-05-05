package herbarium.common.items;

import herbarium.common.Herbarium;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public final class ItemJournal
extends Item {
    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        playerIn.openGui(Herbarium.instance, Herbarium.GUI_JOURNAL, worldIn, ((int) playerIn.posX), ((int) playerIn.posY), ((int) playerIn.posZ));
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }
}