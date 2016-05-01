package herbarium.api.genetics;

import herbarium.api.genetics.allele.IAlleleSpecies;
import net.minecraft.nbt.NBTTagCompound;

public interface IGenome{
    public IAlleleSpecies getPrimary();
    public IAlleleSpecies getSecondary();
    public IAllele getActiveAllele(IChromosomeType type);
    public IAllele getInactiveAllele(IChromosomeType type);
    public IChromosome[] getChromosomes();
    public ISpecies getSpecies();
    public void writeToNBT(NBTTagCompound comp);
}