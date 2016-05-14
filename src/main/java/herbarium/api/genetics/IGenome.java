package herbarium.api.genetics;

import herbarium.api.INBTSavable;
import herbarium.api.genetics.alleles.IAlleleSpecies;

public interface IGenome
    extends INBTSavable {
  public IAlleleSpecies primary();

  public IAlleleSpecies secondary();

  public IAllele activeAllele(IChromosomeType type);

  public IAllele inactiveAllele(IChromosomeType type);

  public IChromosome[] chromosomes();

  public ISpecies species();
}