package herbarium.api.commentarium;

import net.minecraft.nbt.NBTTagCompound;

import java.util.Set;

public interface ICommentarium{
    public Set<IPage> collected();
    public void writeToNBT(NBTTagCompound comp);
    public void readFromNBT(NBTTagCompound comp);
}