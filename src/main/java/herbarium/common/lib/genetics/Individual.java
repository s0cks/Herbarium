package herbarium.common.lib.genetics;

import herbarium.api.genetics.IChromosomeType;
import herbarium.api.genetics.IIndividual;
import net.minecraft.nbt.NBTTagCompound;

public abstract class Individual
implements IIndividual{
    protected boolean analyzed = false;

    protected Individual(){
        this.analyzed = false;
    }

    protected Individual(NBTTagCompound comp){
        this.analyzed = comp.getBoolean("analyzed");
    }

    @Override
    public String getIdentity() {
        return this.getGenome().getPrimary().getUUID();
    }

    @Override
    public String getDisplayName() {
        return this.getGenome().getPrimary().getLocalizedName();
    }

    @Override
    public boolean analyze() {
        if(this.analyzed){
            return false;
        }

        this.analyzed = true;
        return true;
    }

    @Override
    public boolean isAnalyzed() {
        return false;
    }

    @Override
    public boolean isSecret() {
        return this.analyzed;
    }

    @Override
    public boolean isPureBred(IChromosomeType type) {
        return this.getGenome().getActiveAllele(type).getUUID().equals(this.getGenome().getInactiveAllele(type).getUUID());
    }

    @Override
    public void writeToNBT(NBTTagCompound comp){
        comp.setBoolean("analyzed", this.analyzed);
    }
}