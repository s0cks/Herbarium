package herbarium.api.genetics;

import herbarium.api.EnumHumidity;
import herbarium.api.EnumTemperature;
import herbarium.api.genetics.alleles.IAlleleSpecies;

public interface IAlleleSpeciesBuilder{
  public IAlleleSpecies build();
  public IAlleleSpeciesBuilder setTemperature(EnumTemperature temp);
  public IAlleleSpeciesBuilder setHumidity(EnumHumidity humidity);
}