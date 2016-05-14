package herbarium.api.genetics;

import herbarium.api.genetics.alleles.IAlleleSpecies;
import net.minecraft.nbt.NBTTagCompound;

public interface IGenome{
  public IAlleleSpecies primary();

  public IAlleleSpecies secondary();

  public IAllele activeAllele(IChromosomeType type);

  public IAllele inactiveAllele(IChromosomeType type);

  public IChromosome[] chromosomes();

  public ISpecies species();

  public void writeToNBT(NBTTagCompound comp);
}