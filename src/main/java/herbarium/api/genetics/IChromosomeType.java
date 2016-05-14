package herbarium.api.genetics;

public interface IChromosomeType {
  public Class<? extends IAllele> alleleClass();

  public ISpecies species();

  public int ordinal();
}