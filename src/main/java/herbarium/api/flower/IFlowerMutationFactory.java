package herbarium.api.flower;

import herbarium.api.genetics.IAllele;

public interface IFlowerMutationFactory{
    public IFlowerMutation createMutation(IAlleleFlowerSpecies parent0, IAlleleFlowerSpecies parent1, IAllele[] result, int chance);
}