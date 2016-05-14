package herbarium.common.core.genetics;

import herbarium.api.genetics.IChromosomeType;
import herbarium.api.genetics.IIndividual;

public abstract class Individual
    implements IIndividual {
  @Override
  public boolean isPureBred(IChromosomeType type) {
    return this.genome()
               .activeAllele(type)
               .uuid()
               .equals(this.genome()
                           .inactiveAllele(type)
                           .uuid());
  }
}