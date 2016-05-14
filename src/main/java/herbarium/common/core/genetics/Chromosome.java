package herbarium.common.core.genetics;

import herbarium.api.HerbariumApi;
import herbarium.api.genetics.IAllele;
import herbarium.api.genetics.IChromosome;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Random;

public final class Chromosome
    implements IChromosome {
  private static final String PRIMARY_TAG = "Primary";
  private static final String SECONDARY_TAG = "Secondary";

  private IAllele primary;
  private IAllele secondary;

  public Chromosome(IAllele allele) {
    this(allele, allele);
  }

  public Chromosome(NBTTagCompound comp) {
    this(
        HerbariumApi.ALLELE_MANAGER.getAllele(comp.getString(PRIMARY_TAG)),
        HerbariumApi.ALLELE_MANAGER.getAllele(comp.getString(SECONDARY_TAG))
    );
  }

  public Chromosome(IAllele primary, IAllele secondary) {
    this.primary = primary;
    this.secondary = secondary;
  }

  public static IChromosome inherit(Random rand, IChromosome parent0, IChromosome parent1) {
    IAllele choice0 = (rand.nextBoolean() ? parent0.primary() : parent0.secondary());
    IAllele choice1 = (rand.nextBoolean() ? parent1.primary() : parent1.secondary());
    return rand.nextBoolean() ?
               new Chromosome(choice0, choice1) :
               new Chromosome(choice1, choice0);
  }

  @Override
  public IAllele primary() {
    return this.primary;
  }

  @Override
  public IAllele secondary() {
    return this.secondary;
  }

  @Override
  public IAllele inactive() {
    if (!this.secondary.dominant()) return this.secondary;
    if (!this.primary.dominant()) return this.primary;
    return this.secondary;
  }

  @Override
  public IAllele active() {
    if (this.primary.dominant()) return this.primary;
    if (this.secondary.dominant()) return this.secondary;
    return this.primary;
  }

  @Override
  public void writeToNBT(NBTTagCompound comp) {
    comp.setString(PRIMARY_TAG, this.primary.uuid());
    comp.setString(SECONDARY_TAG, this.secondary.uuid());
  }
}