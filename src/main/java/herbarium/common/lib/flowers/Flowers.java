package herbarium.common.lib.flowers;

import herbarium.api.EnumHumidity;
import herbarium.api.flower.EnumFlowerChromosome;
import herbarium.api.flower.FlowerManager;
import herbarium.api.flower.IAlleleFlowerSpecies;
import herbarium.api.flower.IAlleleFlowerSpeciesBuilder;
import herbarium.api.flower.IFlowerGenome;
import herbarium.api.flower.IFlowerMutation;
import herbarium.api.genetics.IAllele;
import herbarium.api.genetics.IAlleleSpeciesBuilder;
import herbarium.common.lib.genetics.Alleles;
import herbarium.common.lib.genetics.EnumAllele;

import java.util.Arrays;

public enum Flowers{
    // Fire
    HOT(FlowerBranches.FIRE, "hot", false){
        @Override
        protected void setSpeciesProperties(IAlleleSpeciesBuilder builder) {
            builder.setHumidity(EnumHumidity.DRY);
        }

        @Override
        protected void setAlleles(IAllele[] alleles) {
            Alleles.AlleleHelper.INSTANCE.set(alleles, EnumFlowerChromosome.FIREPROOF, EnumAllele.Fireproof.TRUE);
            Alleles.AlleleHelper.INSTANCE.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.SHORT);
        }

        @Override
        protected void registerMutations() {
        }
    },
    MILD(FlowerBranches.FIRE, "mild", true){
        @Override
        protected void setSpeciesProperties(IAlleleSpeciesBuilder builder) {

        }

        @Override
        protected void setAlleles(IAllele[] alleles) {

        }

        @Override
        protected void registerMutations() {

        }
    },
    FROST_BURN(FlowerBranches.FROST_BURN, "frost_burn", true){
        @Override
        protected void setSpeciesProperties(IAlleleSpeciesBuilder builder) {

        }

        @Override
        protected void setAlleles(IAllele[] alleles) {

        }

        @Override
        protected void registerMutations() {

        }
    };

    private final FlowerBranches branch;
    private final IAlleleFlowerSpecies species;

    private IAllele[] template;
    private IFlowerGenome genome;

    private Flowers(FlowerBranches branch, String species, boolean dominant){
        String uuid = "hernarium.flower." + this;
        String unlocalizedName = "for.flowers.species." + species;
        this.branch = branch;
        IAlleleFlowerSpeciesBuilder speciesBuilder = FlowerManager.factory.createSpecies(uuid, unlocalizedName, dominant, this.branch.getBranch());
        setSpeciesProperties(speciesBuilder);
        this.species = speciesBuilder.build();
    }

    protected abstract void setSpeciesProperties(IAlleleSpeciesBuilder builder);
    protected abstract void setAlleles(IAllele[] alleles);
    protected abstract void registerMutations();

    public static void initFlowers(){
        for(Flowers flower : values()){
            flower.init();
        }

        for(Flowers flower : values()){
            flower.registerMutations();
        }
    }

    protected final IFlowerMutation registerMutation(Flowers parent0, Flowers parent1, int chance){
        return FlowerManager.mutationFactory.createMutation(parent0.species, parent1.species, getTemplate(), chance);
    }

    public IAllele[] getTemplate(){
        return Arrays.copyOf(this.template, this.template.length);
    }

    private void init(){
        this.template = this.branch.getTemplate();
        Alleles.AlleleHelper.INSTANCE.set(this.template, EnumFlowerChromosome.SPECIES, this.species);
        this.setAlleles(this.template);
        this.genome = FlowerManager.flower.templateAsGenome(this.template);
        FlowerManager.flower.registerTemplate(this.template);
    }

    public IFlowerGenome getGenome(){
        return this.genome;
    }
}