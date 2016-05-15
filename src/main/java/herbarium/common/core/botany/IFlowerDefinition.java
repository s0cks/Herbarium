package herbarium.common.core.botany;

import herbarium.api.botany.IFlower;
import herbarium.api.botany.IFlowerGenome;
import herbarium.api.genetics.IAllele;

public interface IFlowerDefinition {
  public IFlowerGenome genome();

  public IFlower individual();

  public IAllele[] template();
}