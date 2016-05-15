package herbarium.common.core.botany;

import herbarium.api.botany.IFlower;
import herbarium.api.botany.IFlowerGenome;
import herbarium.api.botany.IFlowerSpecies;
import herbarium.api.genetics.IAllele;
import herbarium.common.core.genetics.Species;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public final class FlowerSpecies
extends Species
implements IFlowerSpecies {
  public FlowerSpecies() {
    super("flower");
  }

  @Override
  public IFlower getMember(ItemStack stack) {
    return null;
  }

  @Override
  public IFlower getMember(NBTTagCompound comp) {
    return null;
  }

  @Override
  public IFlower templateAsIndividual(IAllele[] template) {
    return new Flower(templateAsGenome(template));
  }

  @Override
  public IFlowerGenome templateAsGenome(IAllele[] template) {
    return new FlowerGenome(templateAsChromosomes(template));
  }

  @Override
  public IAllele[] defaultTemplate() {
    return Flowers.ALSTROMERIA.template();
  }
}