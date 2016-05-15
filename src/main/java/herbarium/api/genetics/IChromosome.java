package herbarium.api.genetics;

import net.minecraft.nbt.NBTTagCompound;

public interface IChromosome {
  public IAllele primary();

  public IAllele secondary();

  public IAllele inactive();

  public IAllele active();

  public void writeToNBT(NBTTagCompound comp);
}