package herbarium.common.lib.genetics;

import herbarium.api.genetics.AlleleManager;
import herbarium.api.genetics.IAllele;
import herbarium.api.genetics.IChromosome;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Random;

public final class Chromosome
implements IChromosome{
    private static final String UUID0_TAG = "UUID0";
    private static final String UUID1_TAG = "UUID1";

    private final IAllele primary;
    private final IAllele secondary;

    public Chromosome(IAllele allele){
        this(allele, allele);
    }

    public Chromosome(IAllele primary, IAllele secondary){
        this.primary = primary;
        this.secondary = secondary;
    }

    public Chromosome(NBTTagCompound comp){
        this(AlleleManager.registry.getAllele(comp.getString(UUID0_TAG)), AlleleManager.registry.getAllele(comp.getString(UUID1_TAG)));
    }

    public void writeToNBT(NBTTagCompound comp){
        comp.setString(UUID0_TAG, this.primary.getUUID());
        comp.setString(UUID1_TAG, this.secondary.getUUID());
    }

    @Override
    public IAllele getPrimaryAllele() {
        return this.primary;
    }

    @Override
    public IAllele getSecondaryAllele() {
        return this.secondary;
    }

    @Override
    public IAllele getInactiveAllele() {
        if(!this.secondary.isDominant()){
            return this.secondary;
        }

        if(!this.primary.isDominant()){
            return this.primary;
        }

        return this.secondary;
    }

    @Override
    public IAllele getActiveAllele() {
        if(this.primary == null || this.secondary == null){
            return null;
        }

        if(this.primary.isDominant()){
            return this.primary;
        }

        if(this.secondary.isDominant()){
            return this.secondary;
        }

        return this.primary;
    }

    public IAllele getRandom(Random rand){
        return rand.nextBoolean() ?
                       this.primary :
                       this.secondary;
    }

    public static IChromosome inherit(Random rand, IChromosome parent0, IChromosome parent1){
        IAllele choice0 = rand.nextBoolean() ? parent0.getPrimaryAllele() : parent0.getSecondaryAllele();
        IAllele choice1 = rand.nextBoolean() ? parent1.getPrimaryAllele() : parent1.getSecondaryAllele();
        return rand.nextBoolean() ?
                       new Chromosome(choice0, choice1) :
                       new Chromosome(choice1, choice0);
    }
}