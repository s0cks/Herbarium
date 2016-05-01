package herbarium.api.genetics.allele;

import herbarium.api.EnumHumidity;
import herbarium.api.EnumTemperature;
import herbarium.api.genetics.IAllele;
import herbarium.api.genetics.IClassification;
import herbarium.api.genetics.ISpecies;
import net.minecraft.item.ItemStack;

public interface IAlleleSpecies
extends IAllele{
    public ISpecies getSpecies();
    public String getDescription();
    public IClassification getBranch();
    public int getComplexity();
    public float getResearchSuitability(ItemStack stack);
    public EnumTemperature getTemperature();
    public EnumHumidity getHumidity();
    public boolean isSecret();
}