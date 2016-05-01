package herbarium.common.lib.flowers;

import herbarium.api.flower.EnumFlowerChromosome;
import herbarium.api.flower.FlowerManager;
import herbarium.api.flower.IAlleleFlowerSpecies;
import herbarium.api.flower.IFlowerGenome;
import herbarium.api.genetics.IChromosome;
import herbarium.api.genetics.ISpecies;
import herbarium.api.genetics.allele.IAlleleBoolean;
import herbarium.api.genetics.allele.IAlleleInt;
import herbarium.api.genetics.allele.IAlleleSpecies;
import herbarium.common.lib.genetics.Genome;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public final class FlowerGenome
        extends Genome
        implements IFlowerGenome {
    public FlowerGenome(IChromosome[] chromosomes){
        super(chromosomes);
    }

    public FlowerGenome(NBTTagCompound comp){
        super(comp);
    }

    public static IAlleleFlowerSpecies getSpecies(ItemStack stack){
        if(!FlowerManager.flower.isMember(stack)) return null;

        IAlleleSpecies species = getSpeciesDirectly(stack);
        if(species instanceof IAlleleFlowerSpecies) return (IAlleleFlowerSpecies) species;

        return (IAlleleFlowerSpecies) getActiveAllele(stack, EnumFlowerChromosome.SPECIES, FlowerManager.flower);
    }

    @Override
    public IAlleleFlowerSpecies getPrimary() {
        return (IAlleleFlowerSpecies) getActiveAllele(EnumFlowerChromosome.SPECIES);
    }

    @Override
    public IAlleleFlowerSpecies getSecondary() {
        return (IAlleleFlowerSpecies) getInactiveAllele(EnumFlowerChromosome.SPECIES);
    }

    @Override
    public ISpecies getSpecies() {
        return FlowerManager.flower;
    }

    @Override
    public int getLifespan() {
        return ((IAlleleInt) getActiveAllele(EnumFlowerChromosome.LIFESPAN)).get();
    }

    @Override
    public boolean isNocturnal() {
        return ((IAlleleBoolean) getActiveAllele(EnumFlowerChromosome.NOCTURNAL)).get();
    }
}