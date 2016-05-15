package herbarium.api.botany;

import herbarium.api.genetics.IIndividual;

public interface IFlower
extends IIndividual {
  @Override
  public IFlowerGenome genome();
}
