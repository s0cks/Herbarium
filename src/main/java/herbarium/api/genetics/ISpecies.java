package herbarium.api.genetics;

import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.Map;
import java.util.Random;

public interface ISpecies{
    public String getUUID();
    public int getSpeciesCount();
    public Class<? extends IIndividual> getMemberClass();
    public boolean isMember(ItemStack stack);
    public IIndividual getMember(ItemStack stack);
    public ItemStack getMemberStack(IIndividual indvidual);
    public IAllele[] getDefaultTemplate();
    public IAllele[] getRandomTemplate(Random rand);
    public Map<String, IAllele[]> getGenomeTemplates();
    public void registerMutation(IMutation mutation);
    public Collection<? extends IMutation> getMutations();
    public Collection<? extends IMutation> getCombinations(IAllele allele);
    public IChromosomeType[] getKaryotype();
    public IChromosomeType getKaryotypeKey();
}