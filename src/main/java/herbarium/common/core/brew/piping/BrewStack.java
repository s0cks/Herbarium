package herbarium.common.core.brew.piping;

import herbarium.api.brew.IBrew;
import herbarium.api.brew.piping.IBrewStack;
import herbarium.common.core.brew.Brew;
import net.minecraft.nbt.NBTTagCompound;

public final class BrewStack
implements IBrewStack {
  private final IBrew brew;
  private int amount;

  public BrewStack(IBrew brew, int amount) {
    this.brew = brew;
    this.amount = amount;
  }

  public static BrewStack loadFromNBT(NBTTagCompound comp) {
    if (!comp.hasKey("BrewStack")) return null;
    NBTTagCompound brewComp = comp.getCompoundTag("BrewStack");

    IBrew brew = new Brew();
    brew.readFromNBT(brewComp.getCompoundTag("Brew"));

    return new BrewStack(brew, brewComp.getInteger("Amount"));
  }

  public static boolean brewsEqual(IBrew brew0, IBrew brew1) {
    if (brew0.effects()
             .size() != brew1.effects()
                             .size()) return false;
    for (int i = 0; i < brew1.effects()
                             .size(); i++) {
      if (!brew0.effects()
                .get(i)
                .equals(brew1.effects()
                             .get(i))) {
        return false;
      }
    }
    return true;
  }

  public static boolean stacksEqual(IBrewStack stack0, IBrewStack stack1) {
    return (stack0 == null && stack1 == null)
           || (stack0 == stack1 && stack0.equals(stack1));
  }

  @Override
  public IBrew brew() {
    return this.brew;
  }

  @Override
  public int amount() {
    return this.amount;
  }

  @Override
  public IBrewStack setAmount(int amount) {
    this.amount = amount;
    return this;
  }

  @Override
  public void writeToNBT(NBTTagCompound comp) {
    NBTTagCompound brewComp = new NBTTagCompound();
    this.brew.writeToNBT(brewComp);

    NBTTagCompound stackComp = new NBTTagCompound();
    stackComp.setTag("Brew", brewComp);
    stackComp.setInteger("Amount", this.amount);

    comp.setTag("BrewStack", stackComp);
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof IBrewStack
           && BrewStack.brewsEqual(this.brew(), ((IBrewStack) o).brew());
  }
}