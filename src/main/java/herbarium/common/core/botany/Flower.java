package herbarium.common.core.botany;

import herbarium.api.botany.IFlower;
import herbarium.api.botany.IFlowerGenome;
import herbarium.common.core.genetics.Individual;
import net.minecraft.nbt.NBTTagCompound;

public class Flower
extends Individual
implements IFlower {
  private final IFlowerGenome genome;

  public Flower(NBTTagCompound comp) {
    if (comp.hasKey("Genome")) {
      this.genome = new FlowerGenome(comp.getCompoundTag("Genome"));
    } else {
      this.genome = Flowers.ALSTROMERIA.genome();
    }
  }

  public Flower(IFlowerGenome genome) {
    this.genome = genome;
  }

  @Override
  public IFlowerGenome genome() {
    return this.genome;
  }

  @Override
  public String displayName() {
    return null;
  }

  @Override
  public void writeToNBT(NBTTagCompound comp) {
    NBTTagCompound genomeComp = new NBTTagCompound();
    this.genome.writeToNBT(genomeComp);
    comp.setTag("Genome", genomeComp);
  }
}