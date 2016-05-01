package herbarium.api.genetics;

import net.minecraft.nbt.NBTTagCompound;

public interface IIndividual{
    public String getIdentity();
    public String getDisplayName();
    public boolean analyze();
    public boolean isAnalyzed();
    public boolean isSecret();
    public boolean isPureBred(IChromosomeType type);
    public IGenome getGenome();
    public void writeToNBT(NBTTagCompound comp);
}