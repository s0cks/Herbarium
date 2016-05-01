package herbarium.common.lib.flowers;

import herbarium.api.flower.EnumFlowerChromosome;
import herbarium.api.genetics.IAllele;
import herbarium.api.genetics.IClassification;
import herbarium.common.lib.genetics.Alleles;
import herbarium.common.lib.genetics.EnumAllele;
import herbarium.common.lib.genetics.IBranchDefinition;

import java.util.Arrays;

public enum FlowerBranches
implements IBranchDefinition{
    FIRE,
    FROST,
    FROST_BURN;

    private final IClassification branch;

    private FlowerBranches(){
        String name = this.name().toLowerCase();
        String scientific = this.name().substring(0, 1) + this.name().substring(1).toLowerCase();
        this.branch = new BranchFlower(name, scientific);
    }

    private static IAllele[] defaultTemplate;

    @Override
    public IAllele[] getTemplate() {
        if(defaultTemplate == null){
            defaultTemplate = new IAllele[EnumFlowerChromosome.values().length];
            Alleles.AlleleHelper.INSTANCE.set(defaultTemplate, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.NORMAL);
            Alleles.AlleleHelper.INSTANCE.set(defaultTemplate, EnumFlowerChromosome.FIREPROOF, EnumAllele.Fireproof.FALSE);
            Alleles.AlleleHelper.INSTANCE.set(defaultTemplate, EnumFlowerChromosome.NOCTURNAL, EnumAllele.Nocturnal.FALSE);
            Alleles.AlleleHelper.INSTANCE.set(defaultTemplate, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.NORMAL);
        }

        return Arrays.copyOf(defaultTemplate, defaultTemplate.length);
    }

    @Override
    public IClassification getBranch() {
        return this.branch;
    }
}