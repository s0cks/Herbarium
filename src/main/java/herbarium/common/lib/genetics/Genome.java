package herbarium.common.lib.genetics;

import herbarium.api.genetics.IAllele;
import herbarium.api.genetics.IChromosome;
import herbarium.api.genetics.IChromosomeType;
import herbarium.api.genetics.IGenome;
import herbarium.api.genetics.ISpecies;
import herbarium.api.genetics.allele.IAlleleSpecies;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.Arrays;

public abstract class Genome
implements IGenome {
    private static final String SLOT_TAG = "Slot";

    private static final class GenomeCompound{
        private static final String TAG = "Genom";

        public static NBTTagCompound getCompound(ItemStack stack){
            if(stack == null) return null;
            if(!stack.hasTagCompound()) return null;
            if(!stack.getTagCompound().hasKey(TAG)) return null;
            return stack.getTagCompound().getCompoundTag(TAG);
        }
    }

    private final IChromosome[] chromosomes;

    protected Genome(NBTTagCompound comp){
        this.chromosomes = getChromosomes(comp, getSpecies());
    }

    protected Genome(IChromosome[] chromosomes){
        this.chromosomes = chromosomes;
    }

    private IAllele[] getDefaultTemplate(){
        return getSpecies().getDefaultTemplate();
    }

    protected static IAlleleSpecies getSpeciesDirectly(ItemStack stack){
        NBTTagCompound comp = GenomeCompound.getCompound(stack);
        if(comp == null) return null;

        NBTTagList list = comp.getTagList("Chromosomes", 10);
        if(list == null) return null;

        NBTTagCompound chromosomeNBT = list.getCompoundTagAt(0);
        Chromosome chromosome = new Chromosome(chromosomeNBT);

        IAllele active = chromosome.getActiveAllele();
        if(!(active instanceof IAlleleSpecies)) return null;

        return (IAlleleSpecies) active;
    }

    private static IChromosome getChromosome(ItemStack stack, IChromosomeType type, ISpecies species){
        NBTTagCompound genome = GenomeCompound.getCompound(stack);
        if(genome == null) return null;

        IChromosome chromosomes[] = getChromosomes(genome, species);
        if(chromosomes == null) return null;

        return chromosomes[type.ordinal()];
    }

    private static IChromosome[] getChromosomes(NBTTagCompound comp, ISpecies species){
        NBTTagList chromosomesNBT = comp.getTagList("Chromosomes", 10);
        IChromosome[] chromosomes = new IChromosome[species.getDefaultTemplate().length];

        for(int i = 0; i < chromosomes.length; i++){
            NBTTagCompound chromosomeNBT = chromosomesNBT.getCompoundTagAt(i);
            byte ordinal = chromosomeNBT.getByte(SLOT_TAG);

            if(ordinal >= 0 && ordinal < chromosomes.length){
                chromosomes[ordinal] = new Chromosome(chromosomeNBT);
            }
        }

        return chromosomes;
    }

    protected static IAllele getActiveAllele(ItemStack stack, IChromosomeType type, ISpecies species){
        IChromosome chromosome = getChromosome(stack, type, species);
        return chromosome == null ? null : chromosome.getActiveAllele();
    }

    @Override
    public void writeToNBT(NBTTagCompound comp){
        NBTTagList list = new NBTTagList();
        for(int i = 0; i < this.chromosomes.length; i++){
            if(this.chromosomes[i] != null){
                NBTTagCompound c = new NBTTagCompound();
                c.setByte(SLOT_TAG, (byte) i);
                this.chromosomes[i].writeToNBT(c);
            }
        }
        comp.setTag("Chromosomes", list);
    }

    @Override
    public IChromosome[] getChromosomes(){
        return Arrays.copyOf(this.chromosomes, this.chromosomes.length);
    }

    @Override
    public IAllele getActiveAllele(IChromosomeType type){
        return this.chromosomes[type.ordinal()].getActiveAllele();
    }

    @Override
    public IAllele getInactiveAllele(IChromosomeType type){
        return this.chromosomes[type.ordinal()].getInactiveAllele();
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof IGenome)) return false;

        IGenome genome = (IGenome) obj;
        IChromosome[] genetics = genome.getChromosomes();
        if(this.chromosomes.length != genetics.length) return false;

        for(int i = 0; i < this.chromosomes.length; i++){
            IChromosome chromosome = this.chromosomes[i];
            IChromosome otherChromosome = genetics[i];

            if((chromosome == null) && (otherChromosome == null)) continue;
            if((chromosome == null) || (otherChromosome == null)) return false;
            if(!chromosome.getPrimaryAllele().getUUID().equals(otherChromosome.getPrimaryAllele().getUUID())) return false;
            if(!chromosome.getSecondaryAllele().getUUID().equals(otherChromosome.getSecondaryAllele().getUUID())) return false;
        }

        return true;
    }
}