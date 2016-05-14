package herbarium.api.genetics;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface ISpecies {
  public String uuid();

  public IIndividual getMember(ItemStack stack);

  public IIndividual getMember(NBTTagCompound comp);

  public IIndividual templateAsIndividual(IAllele[] template);

  public boolean isMember(ItemStack stack);

  public IChromosome[] templateAsChromosomes(IAllele[] template);

  public IGenome templateAsGenome(IAllele[] template);

  public void registerTemplate(String ident, IAllele[] template);

  public void registerMutation(IMutation mutation);

  public IAllele[] getTemplate(String ident);

  public IAllele[] defaultTemplate();

  public IAllele[] randomTemplate();
}