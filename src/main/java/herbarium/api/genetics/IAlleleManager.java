package herbarium.api.genetics;

public interface IAlleleManager {
  public void registerSpecies(ISpecies species);

  public void registerAllele(IAllele allele, IChromosomeType type);

  public ISpecies getSpecies(String uuid);

  public IAllele getAllele(String uuid);
}