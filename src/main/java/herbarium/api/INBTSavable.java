package herbarium.api;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTSavable{
    public void readFromNBT(NBTTagCompound comp);
    public void writeToNBT(NBTTagCompound comp);
}