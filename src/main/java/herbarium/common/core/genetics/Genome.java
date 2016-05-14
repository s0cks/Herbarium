package herbarium.common.core.genetics;

import herbarium.api.botany.EnumFlowerChromosome;
import herbarium.api.genetics.IAllele;
import herbarium.api.genetics.IChromosome;
import herbarium.api.genetics.IChromosomeType;
import herbarium.api.genetics.IGenome;
import herbarium.api.genetics.alleles.IAlleleSpecies;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.Arrays;

public abstract class Genome
    implements IGenome {
  private final IChromosome[] chromosomes;

  public Genome(IChromosome[] chromosomes) {
    this.chromosomes = chromosomes;
  }

  public Genome(NBTTagCompound comp) {
    NBTTagList chromosomesNBT = comp.getTagList("Chromosomes", 10);
    this.chromosomes = new IChromosome[species().defaultTemplate().length];

    for (int i = 0; i < chromosomesNBT.tagCount(); i++) {
      NBTTagCompound chromosomeNBT = chromosomesNBT.getCompoundTagAt(i);
      byte ordinal = chromosomeNBT.getByte("Ordinal");
      if (ordinal >= 0 && ordinal < this.chromosomes.length) {
        Chromosome chromosome = new Chromosome(chromosomeNBT);
        this.chromosomes[ordinal] = chromosome;
      }
    }
  }

  @Override
  public IAlleleSpecies primary() {
    return ((IAlleleSpecies) this.activeAllele(EnumFlowerChromosome.SPECIES));
  }

  @Override
  public IAlleleSpecies secondary() {
    return ((IAlleleSpecies) this.inactiveAllele(EnumFlowerChromosome.SPECIES));
  }

  @Override
  public IAllele activeAllele(IChromosomeType type) {
    return this.chromosomes[type.ordinal()].active();
  }

  @Override
  public IAllele inactiveAllele(IChromosomeType type) {
    return this.chromosomes[type.ordinal()].inactive();
  }

  @Override
  public IChromosome[] chromosomes() {
    return Arrays.copyOf(this.chromosomes, this.chromosomes.length);
  }

  @Override
  public void writeToNBT(NBTTagCompound comp) {
    NBTTagList chromosomesList = new NBTTagList();
    for(int i = 0; i < this.chromosomes.length; i++){
      NBTTagCompound chromosomeComp = new NBTTagCompound();
      chromosomeComp.setByte("Ordinal", (byte) i);
      this.chromosomes[i].writeToNBT(chromosomeComp);
      chromosomesList.appendTag(chromosomeComp);
    }
    comp.setTag("Chromosomes", chromosomesList);
  }
}