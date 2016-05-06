package herbarium.api.genetics;

import net.minecraft.item.ItemStack;

public interface ISpecies{
    public String uuid();
    public boolean isMember(ItemStack stack);
    public IChromosome[] templateAsChromosomes(IAllele[] template);
    public void registerTemplate(String ident, IAllele[] template);
    public void registerMutation(IMutation mutation);
    public IAllele[] getTemplate(String ident);
    public IAllele[] defaultTemplate();
    public IAllele[] randomTemplate();
}