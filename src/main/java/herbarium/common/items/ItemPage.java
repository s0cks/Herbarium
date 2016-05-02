package herbarium.common.items;

import herbarium.api.HerbariumApi;
import herbarium.api.commentarium.IPage;
import herbarium.common.core.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

public final class ItemPage
extends Item {
    private static final String PAGE_TAG = "Page";

    public static void setPage(ItemStack stack, IPage page){
        NBTTagCompound comp = NBTHelper.getCompound(stack);
        if(comp == null) comp = new NBTTagCompound();
        comp.setString(PAGE_TAG, page.uuid());
        if(!stack.hasTagCompound()) stack.setTagCompound(comp);
    }

    public static IPage getPage(ItemStack stack){
        NBTTagCompound comp = NBTHelper.getCompound(stack);
        if(comp == null) return null;
        return HerbariumApi.PAGE_MANAGER.get(comp.getString(PAGE_TAG));
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
        IPage page = getPage(itemStackIn);
        if(page != null){
            HerbariumApi.PAGE_TRACKER.learn(playerIn, page);
            itemStackIn.stackSize--;
        }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        IPage page = getPage(stack);
        if(page == null) return;
        tooltip.add("Page " + page.ordinal() + 1 + ": " + page.title());
    }
}