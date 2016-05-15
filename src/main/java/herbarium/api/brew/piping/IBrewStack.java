package herbarium.api.brew.piping;

import herbarium.api.brew.IBrew;
import net.minecraft.nbt.NBTTagCompound;

public interface IBrewStack {
  public static final int MAX_AMOUNT = 100;

  public IBrew brew();

  public int amount();

  public IBrewStack setAmount(int amount);

  public void writeToNBT(NBTTagCompound comp);
}