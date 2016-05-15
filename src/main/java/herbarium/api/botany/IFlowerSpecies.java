package herbarium.api.botany;

import herbarium.api.genetics.IAllele;
import herbarium.api.genetics.ISpecies;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IFlowerSpecies
extends ISpecies {
  @Override
  public IFlower getMember(ItemStack stack);

  @Override
  public IFlower getMember(NBTTagCompound comp);

  @Override
  public IFlower templateAsIndividual(IAllele[] template);

  @Override
  public IFlowerGenome templateAsGenome(IAllele[] template);
}