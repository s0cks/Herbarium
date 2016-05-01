package herbarium.api.genetics;

import net.minecraft.nbt.NBTTagCompound;

public interface IChromosome{
    public IAllele getPrimaryAllele();
    public IAllele getSecondaryAllele();
    public IAllele getInactiveAllele();
    public IAllele getActiveAllele();
    public void writeToNBT(NBTTagCompound comp);
}