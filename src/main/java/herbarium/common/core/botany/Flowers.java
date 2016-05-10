package herbarium.common.core.botany;

import herbarium.api.botany.IAlleleFlowerSpecies;
import herbarium.api.botany.IFlowerGenome;
import herbarium.api.genetics.IAllele;

public enum Flowers{
    ;

    private IAlleleFlowerSpecies species;
    private IAllele[] template;
    private IFlowerGenome genome;

    Flowers(String name){
    }
}