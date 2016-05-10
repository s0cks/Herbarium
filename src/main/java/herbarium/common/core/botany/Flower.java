package herbarium.common.core.botany;

import herbarium.api.botany.IFlower;
import herbarium.api.botany.IFlowerGenome;
import herbarium.common.core.genetics.Individual;
import net.minecraft.nbt.NBTTagCompound;

public class Flower
        extends Individual
        implements IFlower {
    private final IFlowerGenome genome;

    public Flower(NBTTagCompound comp){
        if(comp.hasKey("Genome")){
            this.genome = FlowerGenome.fromNBT(comp.getCompoundTag("Genome"));
        } else{
            this.genome = null;
        }
    }

    @Override
    public IFlowerGenome genome() {
        return null;
    }

    @Override
    public String displayName() {
        return null;
    }

    @Override
    public void readFromNBT(NBTTagCompound comp) {

    }

    @Override
    public void writeToNBT(NBTTagCompound comp) {

    }
}