package herbarium.api.genetics;

import herbarium.api.INBTSavable;

public interface IIndividual
    extends INBTSavable {
  public IGenome genome();

  public String displayName();

  public boolean isPureBred(IChromosomeType type);
}