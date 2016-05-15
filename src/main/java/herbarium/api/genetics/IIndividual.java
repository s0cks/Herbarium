package herbarium.api.genetics;

import net.minecraft.nbt.NBTTagCompound;

public interface IIndividual {
  public IGenome genome();

  public String displayName();

  public boolean isPureBred(IChromosomeType type);

  public void writeToNBT(NBTTagCompound comp);
}