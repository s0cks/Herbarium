package herbarium.common.core.botany;

import herbarium.api.botany.IAlleleFlowerSpeciesBuilder;
import herbarium.api.botany.IFlowerFactory;
import herbarium.common.core.botany.alleles.AlleleFlowerSpecies;

public final class FlowerFactory
implements IFlowerFactory{
  @Override
  public IAlleleFlowerSpeciesBuilder createSpecies(String uuid, boolean dominant) {
    return new AlleleFlowerSpecies(uuid, dominant);
  }
}