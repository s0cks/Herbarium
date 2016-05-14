package herbarium.api.botany;

import herbarium.api.brew.effects.IEffect;
import herbarium.api.genetics.IAllele;

public interface IAlleleFlowerEffect
    extends IAllele{
  public IEffect effect();
}