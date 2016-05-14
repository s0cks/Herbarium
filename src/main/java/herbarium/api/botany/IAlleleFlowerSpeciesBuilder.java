package herbarium.api.botany;

import herbarium.api.genetics.IAlleleSpeciesBuilder;

public interface IAlleleFlowerSpeciesBuilder
extends IAlleleSpeciesBuilder{
  @Override public IAlleleFlowerSpecies build();
}