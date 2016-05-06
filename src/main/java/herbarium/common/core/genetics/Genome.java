package herbarium.common.core.genetics;

import herbarium.api.EnumFlowerChromosome;
import herbarium.api.HerbariumApi;
import herbarium.api.genetics.IAllele;
import herbarium.api.genetics.IChromosome;
import herbarium.api.genetics.IChromosomeType;
import herbarium.api.genetics.IGenome;
import herbarium.api.genetics.ISpecies;
import herbarium.api.genetics.alleles.IAlleleSpecies;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Arrays;

public final class Genome
implements IGenome {
    private final IChromosome[] chromosomes;

    public Genome(IChromosome[] chromosomes){
        this.chromosomes = chromosomes;
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
    public ISpecies species() {
        return HerbariumApi.SPECIES_FLOWER;
    }

    @Override
    public void readFromNBT(NBTTagCompound comp) {

    }

    @Override
    public void writeToNBT(NBTTagCompound comp) {

    }
}