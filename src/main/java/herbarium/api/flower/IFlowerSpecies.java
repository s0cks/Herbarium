package herbarium.api.flower;

import herbarium.api.genetics.IAllele;
import herbarium.api.genetics.ISpecies;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public interface IFlowerSpecies
extends ISpecies {
    public IFlower getMember(ItemStack stack);
    public IFlower getMember(NBTTagCompound comp);
    public IFlower templateAsIndividual(IAllele[] template);
    public IFlowerGenome templateAsGenome(IAllele[] template);
    public List<IFlower> getTemplates();
    public List<IFlowerMutation> getMutations();
    public void registerTemplate(IAllele[] alleles);
}