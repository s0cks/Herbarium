package herbarium.api.botany;

import herbarium.api.genetics.alleles.IAlleleSpecies;

public interface IAlleleFlowerSpecies
extends IAlleleSpecies{
    @Override public IFlowerSpecies species();
}