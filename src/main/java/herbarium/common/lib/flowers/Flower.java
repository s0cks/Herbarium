package herbarium.common.lib.flowers;

import herbarium.api.flower.EnumFlowerChromosome;
import herbarium.api.flower.IFlower;
import herbarium.api.flower.IFlowerGenome;
import herbarium.common.lib.genetics.Individual;
import net.minecraft.nbt.NBTTagCompound;

public final class Flower
extends Individual
implements IFlower{
    private final IFlowerGenome genome;
    private IFlowerGenome mate;

    public Flower(IFlowerGenome genome){
        this.genome = genome;
    }

    public Flower(NBTTagCompound comp){
        super(comp);
        if(comp.hasKey("Genome")){
            this.genome = new FlowerGenome(comp.getCompoundTag("Genome"));
        } else{
            this.genome = Flowers.MILD.getGenome();
        }

        if(comp.hasKey("Mate")){
            this.mate = new FlowerGenome(comp.getCompoundTag("Mate"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound comp){
        super.writeToNBT(comp);

        NBTTagCompound genomeNBT = new NBTTagCompound();
        this.genome.writeToNBT(genomeNBT);
        comp.setTag("Genome", genomeNBT);

        if(this.mate != null){
            NBTTagCompound mateNBT = new NBTTagCompound();
            this.mate.writeToNBT(mateNBT);
            comp.setTag("Mate", mateNBT);
        }
    }

    @Override
    public void mate(IFlower flower) {
        this.mate = new FlowerGenome(flower.getGenome().getChromosomes());
    }

    @Override
    public IFlowerGenome getGenome() {
        return this.genome;
    }

    @Override
    public IFlowerGenome getMate() {
        return this.mate;
    }

    @Override
    public boolean isPureBred(EnumFlowerChromosome chromosome) {
        return this.genome.getActiveAllele(chromosome).getUUID().equals(this.genome.getInactiveAllele(chromosome).getUUID());
    }
}