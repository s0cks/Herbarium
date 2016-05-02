package herbarium.common.items;

import herbarium.api.HerbariumApi;
import herbarium.api.IFlower;
import herbarium.common.core.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public final class ItemDust
extends Item {
    private static final String TYPE_TAG = "Type";

    public static void setFlower(ItemStack stack, IFlower flower){
        NBTTagCompound comp = NBTHelper.getCompound(stack);
        if(comp == null) comp = new NBTTagCompound();
        comp.setString(TYPE_TAG, flower.uuid());
        if(!stack.hasTagCompound()) stack.setTagCompound(comp);
    }

    public static IFlower getFlower(ItemStack stack){
        NBTTagCompound comp = NBTHelper.getCompound(stack);
        if(comp == null) return null;
        if(!comp.hasKey(TYPE_TAG)) return null;
        return HerbariumApi.FLOWER_MANAGER.getFlower(comp.getString(TYPE_TAG));
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        IFlower flower = getFlower(stack);
        if(flower != null){
            tooltip.add("Dust of " + flower.name());
        }
    }
}