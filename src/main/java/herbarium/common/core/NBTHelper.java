package herbarium.common.core;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public final class NBTHelper{
    public static NBTTagCompound getCompound(ItemStack stack){
        if(stack == null) return null;
        if(!stack.hasTagCompound()) return null;
        return stack.getTagCompound();
    }
}