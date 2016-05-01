package herbarium.api.flower;

import herbarium.api.genetics.IAllele;
import herbarium.api.genetics.IChromosomeType;
import herbarium.api.genetics.ISpecies;
import herbarium.api.genetics.allele.IAlleleBoolean;
import herbarium.api.genetics.allele.IAlleleFloat;
import herbarium.api.genetics.allele.IAlleleInt;

public enum EnumFlowerChromosome
implements IChromosomeType{
    SPECIES(IAlleleFlowerSpecies.class),
    FERTILITY(IAlleleFloat.class),
    YIELD(IAlleleFloat.class),
    FIREPROOF(IAlleleBoolean.class),
    LIFESPAN(IAlleleInt.class),
    NOCTURNAL(IAlleleBoolean.class);

    private final Class<? extends IAllele> alleleClass;

    private EnumFlowerChromosome(Class<? extends IAllele> alleleClass){
        this.alleleClass = alleleClass;
    }

    @Override
    public Class<? extends IAllele> getAlleleClass() {
        return this.alleleClass;
    }

    @Override
    public String getLocalizedName() {
        return this.name().toLowerCase();
    }

    @Override
    public ISpecies getSpecies() {
        return FlowerManager.flower;
    }
}