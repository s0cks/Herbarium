package herbarium.common.core.genetics;

import herbarium.api.genetics.IAllele;
import herbarium.api.genetics.IChromosome;
import herbarium.api.genetics.IMutation;
import herbarium.api.genetics.ISpecies;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public abstract class Species
implements ISpecies {
    private final Map<String, IAllele[]> templates = new HashMap<>();

    private final String uuid;

    public Species(String uuid){
        this.uuid = uuid;
    }

    @Override
    public String uuid() {
        return this.uuid;
    }

    @Override
    public boolean isMember(ItemStack stack) {
        return false;
    }

    @Override
    public IChromosome[] templateAsChromosomes(IAllele[] template) {
        return new IChromosome[0];
    }

    @Override
    public void registerTemplate(String ident, IAllele[] template) {
        if(template == null) return;
        if(template.length == 0) return;
        this.templates.put(ident, template);
    }

    @Override
    public void registerMutation(IMutation mutation) {

    }

    @Override
    public IAllele[] getTemplate(String ident) {
        return new IAllele[0];
    }

    @Override
    public IAllele[] defaultTemplate() {
        return new IAllele[0];
    }

    @Override
    public IAllele[] randomTemplate() {
        return new IAllele[0];
    }
}