package herbarium.common.lib.flowers;

import herbarium.api.flower.FlowerManager;
import herbarium.api.flower.IAlleleFlowerSpecies;
import herbarium.api.flower.IFlowerMutation;
import herbarium.api.genetics.IAllele;
import herbarium.api.genetics.ISpecies;
import herbarium.common.lib.genetics.mutation.Mutation;

public final class FlowerMutation
extends Mutation
implements IFlowerMutation{
    public FlowerMutation(IAlleleFlowerSpecies allele0, IAlleleFlowerSpecies allele1, IAllele[] template, int chance){
        super(allele0, allele1, template, chance);
    }

    @Override
    public ISpecies getSpecies() {
        return FlowerManager.flower;
    }
}