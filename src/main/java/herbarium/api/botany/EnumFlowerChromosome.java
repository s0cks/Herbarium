package herbarium.api.botany;

import herbarium.api.HerbariumApi;
import herbarium.api.genetics.IChromosomeType;
import herbarium.api.genetics.ISpecies;

public enum EnumFlowerChromosome
    implements IChromosomeType {
  SPECIES;

  @Override
  public ISpecies species() {
    return HerbariumApi.FLOWER_SPECIES;
  }
}