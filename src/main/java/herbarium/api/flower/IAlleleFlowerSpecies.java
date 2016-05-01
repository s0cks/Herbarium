package herbarium.api.flower;

import herbarium.api.flower.IFlowerSpecies;
import herbarium.api.genetics.allele.IAlleleSpecies;
import net.minecraft.item.ItemStack;

import java.util.Map;

public interface IAlleleFlowerSpecies
extends IAlleleSpecies {
    public IFlowerSpecies getSpecies();
    public String getEntityTexture();
    public String getItemTexture();
    public boolean isNocturnal();
    public Map<ItemStack, Float> getHarvestLoot();
    public Map<ItemStack, Float> getFullHarvestLoot();
}