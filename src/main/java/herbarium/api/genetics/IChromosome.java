package herbarium.api.genetics;

import herbarium.api.INBTSavable;

public interface IChromosome
    extends INBTSavable {
  public IAllele primary();

  public IAllele secondary();

  public IAllele inactive();

  public IAllele active();
}