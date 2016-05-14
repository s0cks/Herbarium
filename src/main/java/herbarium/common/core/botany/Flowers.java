package herbarium.common.core.botany;

import herbarium.api.botany.EnumFlowerChromosome;
import herbarium.api.HerbariumApi;
import herbarium.api.botany.IAlleleFlowerSpecies;
import herbarium.api.botany.IAlleleFlowerSpeciesBuilder;
import herbarium.api.botany.IFlower;
import herbarium.api.botany.IFlowerGenome;
import herbarium.api.genetics.IAllele;
import herbarium.common.core.genetics.alleles.AlleleHelper;

import java.util.Arrays;

public enum Flowers
implements IFlowerDefinition{
  ALSTROMERIA(FlowerBranches.SPIRIT, true){
    @Override
    protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder species) {

    }

    @Override
    protected void setAlleles(IAllele[] template) {

    }

    @Override
    protected void registerMutations() {

    }
  },
  BELLADONNA(FlowerBranches.SPIRIT, true){
    @Override
    protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder species) {

    }

    @Override
    protected void setAlleles(IAllele[] template) {

    }

    @Override
    protected void registerMutations() {

    }
  },
  BLUE_ANEMONE(FlowerBranches.SPIRIT, true) {
    @Override
    protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder species) {

    }

    @Override
    protected void setAlleles(IAllele[] template) {

    }

    @Override
    protected void registerMutations() {

    }
  },
  BLUEBERRY_BLOSSOM(FlowerBranches.SPIRIT, true) {
    @Override
    protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder species) {

    }

    @Override
    protected void setAlleles(IAllele[] template) {

    }

    @Override
    protected void registerMutations() {

    }
  },
  BUTTERCUP(FlowerBranches.SPIRIT, true){
    @Override
    protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder species) {

    }

    @Override
    protected void setAlleles(IAllele[] template) {

    }

    @Override
    protected void registerMutations() {

    }
  },
  CAVERN_BLOOM(FlowerBranches.SPIRIT, true){
    @Override
    protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder species) {

    }

    @Override
    protected void setAlleles(IAllele[] template) {

    }

    @Override
    protected void registerMutations() {

    }
  },
  WINTER_LILY(FlowerBranches.SPIRIT, true){
    @Override
    protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder species) {

    }

    @Override
    protected void setAlleles(IAllele[] template) {

    }

    @Override
    protected void registerMutations() {

    }
  },
  LANCET_ROOT(FlowerBranches.SPIRIT, true){
    @Override
    protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder species) {

    }

    @Override
    protected void setAlleles(IAllele[] template) {

    }

    @Override
    protected void registerMutations() {

    }
  },
  TAIL_IRIS(FlowerBranches.SPIRIT, true){
    @Override
    protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder species) {

    }

    @Override
    protected void setAlleles(IAllele[] template) {

    }

    @Override
    protected void registerMutations() {

    }
  },
  SPRING_LOTUS(FlowerBranches.SPIRIT, true){
    @Override
    protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder species) {

    }

    @Override
    protected void setAlleles(IAllele[] template) {

    }

    @Override
    protected void registerMutations() {

    }
  },
  IGNEOUS_SPEAR(FlowerBranches.SPIRIT, true){
    @Override
    protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder species) {

    }

    @Override
    protected void setAlleles(IAllele[] template) {

    }

    @Override
    protected void registerMutations() {

    }
  },
  TROPICAL_BERRIES(FlowerBranches.SPIRIT, true){
    @Override
    protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder species) {

    }

    @Override
    protected void setAlleles(IAllele[] template) {

    }

    @Override
    protected void registerMutations() {

    }
  };

  private final FlowerBranches branch;
  private final IAlleleFlowerSpecies species;
  private IAllele[] template;
  private IFlowerGenome genome;

  Flowers(FlowerBranches branch, boolean dominant) {
    this.branch = branch;

    String uuid = "herbarium.flower.species." + this.name().toLowerCase();

    IAlleleFlowerSpeciesBuilder builder = HerbariumApi.FLOWER_FACTORY
        .createSpecies(uuid, dominant);
    this.setSpeciesProperties(builder);
    this.species = builder.build();
  }

  protected abstract void setSpeciesProperties(IAlleleFlowerSpeciesBuilder species);
  protected abstract void setAlleles(IAllele[] template);
  protected abstract void registerMutations();

  public static void initFlowers(){
    for(Flowers flower : values()) flower.init();
  }

  private void init(){
    this.template = this.branch.template();
    AlleleHelper.set(this.template, EnumFlowerChromosome.SPECIES, this.species);
    this.genome = HerbariumApi.FLOWER_SPECIES.templateAsGenome(this.template);
    HerbariumApi.FLOWER_SPECIES.registerTemplate(template[0].uuid(), template);
  }

  @Override
  public IAllele[] template() {
    return Arrays.copyOf(this.template, this.template.length);
  }

  @Override
  public IFlowerGenome genome() {
    return this.genome;
  }


  @Override
  public IFlower individual() {
    return new Flower(this.genome);
  }
}