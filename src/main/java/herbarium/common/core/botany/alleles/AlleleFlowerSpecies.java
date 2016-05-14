package herbarium.common.core.botany.alleles;

import herbarium.api.EnumHumidity;
import herbarium.api.EnumTemperature;
import herbarium.api.botany.IAlleleFlowerSpecies;
import herbarium.api.botany.IFlowerSpecies;
import herbarium.common.core.genetics.alleles.AlleleSpecies;

public final class AlleleFlowerSpecies
    extends AlleleSpecies
    implements IAlleleFlowerSpecies {
  protected AlleleFlowerSpecies(String uuid, boolean dominant) {
    super(uuid, dominant);
  }

  @Override
  public IFlowerSpecies species() {
    return null;
  }

  @Override
  public String description() {
    return null;
  }

  @Override
  public EnumHumidity humidity() {
    return null;
  }

  @Override
  public EnumTemperature temperature() {
    return null;
  }
}