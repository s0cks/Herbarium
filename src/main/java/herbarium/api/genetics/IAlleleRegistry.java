package herbarium.api.genetics;

import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.Map;

public interface IAlleleRegistry{
    public IClassification getClassification(String uuid);
    public void register(Object obj);
    public IClassification createAndRegisterClassification(IClassification.EnumClassificationLevel level, String uuid, String scientific);
    public IClassification createAndRegisterClassification(IClassification.EnumClassificationLevel level, String uuid, String scientific, IClassification... members);
    public void registerClassification(IClassification classification);
    public void registerSpecies(ISpecies species);
    public void registerAllele(IAllele allele, IChromosomeType... types);
    public IAllele getAllele(String uuid);
    public Collection<IAllele> getAlleles(IChromosomeType type);
    public ISpecies getSpecies(String uuid);
    public ISpecies getSpecies(ItemStack stack);
    public ISpecies getSpecies(Class<? extends IIndividual> clz);
    public Map<String, ISpecies> getSpecies();
    public Map<String, IAllele> getAlleles();
    public Map<String, IClassification> getClassifications();
    public boolean isIndividual(ItemStack stack);
    public IIndividual getIndividual(ItemStack stack);
}