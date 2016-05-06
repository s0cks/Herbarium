package herbarium.api;

import herbarium.api.genetics.IAllele;
import herbarium.api.genetics.IChromosomeType;
import herbarium.api.genetics.ISpecies;
import herbarium.api.genetics.alleles.IAlleleSpecies;

public enum EnumFlowerChromosome
implements IChromosomeType{
    SPECIES(IAlleleSpecies.class);

    private final Class<? extends IAllele> clazz;

    EnumFlowerChromosome(Class<? extends IAllele> clazz){
        this.clazz = clazz;
    }

    @Override
    public Class<? extends IAllele> alleleClass() {
        return this.clazz;
    }

    @Override
    public ISpecies species() {
        return HerbariumApi.SPECIES_FLOWER;
    }
}