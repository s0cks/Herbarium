package herbarium.api.botany;

import herbarium.api.genetics.IGenome;

public interface IFlowerGenome
    extends IGenome {
  @Override
  public IAlleleFlowerSpecies primary();

  @Override
  public IAlleleFlowerSpecies secondary();
}
