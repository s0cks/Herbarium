package herbarium.common.core.botany.alleles;

import herbarium.api.EnumHumidity;
import herbarium.api.EnumTemperature;
import herbarium.api.HerbariumApi;
import herbarium.api.botany.EnumFlowerChromosome;
import herbarium.api.botany.IAlleleFlowerSpecies;
import herbarium.api.botany.IAlleleFlowerSpeciesBuilder;
import herbarium.api.botany.IFlowerSpecies;
import herbarium.api.genetics.IAlleleSpeciesBuilder;
import herbarium.common.core.genetics.alleles.AlleleSpecies;

public final class AlleleFlowerSpecies
extends AlleleSpecies
implements IAlleleFlowerSpecies,
           IAlleleFlowerSpeciesBuilder {
  private EnumTemperature temperature = EnumTemperature.NORMAL;
  private EnumHumidity humidity = EnumHumidity.NORMAL;

  public AlleleFlowerSpecies(String uuid, boolean dominant) {
    super(uuid, dominant);
  }

  @Override
  public IFlowerSpecies species() {
    return HerbariumApi.FLOWER_SPECIES;
  }

  //TODO: Fixme
  @Override
  public String description() {
    return "";
  }

  @Override
  public EnumTemperature temperature() {
    return this.temperature;
  }

  @Override
  public EnumHumidity humidity() {
    return this.humidity;
  }

  @Override
  public IAlleleFlowerSpecies build() {
    HerbariumApi.ALLELE_MANAGER.registerAllele(this, EnumFlowerChromosome.SPECIES);
    return this;
  }

  @Override
  public IAlleleSpeciesBuilder setTemperature(EnumTemperature temp) {
    this.temperature = temp;
    return this;
  }

  @Override
  public IAlleleSpeciesBuilder setHumidity(EnumHumidity humidity) {
    this.humidity = humidity;
    return this;
  }
}