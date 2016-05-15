package herbarium.api.genetics.alleles;

import herbarium.api.EnumHumidity;
import herbarium.api.EnumTemperature;
import herbarium.api.genetics.IAllele;
import herbarium.api.genetics.ISpecies;

public interface IAlleleSpecies
extends IAllele {
  public ISpecies species();

  public String description();

  public EnumTemperature temperature();

  public EnumHumidity humidity();
}