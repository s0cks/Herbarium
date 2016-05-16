package herbarium.common.tiles.core;

import herbarium.api.brew.IBrew;
import herbarium.api.brew.piping.IBrewSource;
import herbarium.common.core.brew.piping.BrewStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public abstract class TileEntityBrewSource
extends TileEntityHerbarium
implements IBrewSource {
  protected BrewStack stack;

  @Override
  public void readFromNBT(NBTTagCompound compound) {
    super.readFromNBT(compound);
    if (compound.hasKey("Stack")) {
      this.stack = BrewStack.loadFromNBT(compound.getCompoundTag("Stack"));
    }
  }

  @Override
  public void writeToNBT(NBTTagCompound compound) {
    super.writeToNBT(compound);
    if (this.stack != null) {
      NBTTagCompound stackComp = new NBTTagCompound();
      this.stack.writeToNBT(stackComp);
      compound.setTag("Stack", stackComp);
    }
  }

  @Override
  public IBrew brew() {
    return this.stack != null
           ? this.stack.brew()
           : null;
  }

  @Override
  public int amount() {
    return this.stack == null
           ? 0
           : this.stack.amount();
  }

  @Override
  public int extract(IBrew brew, int amount, EnumFacing facing) {
    if (this.brew() == null || brew == null) return 0;
    if ((this.stack != null && BrewStack.brewsEqual(this.stack.brew(), brew)) && this.canOutputTo(facing)) {
      int drained = amount;
      if (this.amount() < drained) {
        drained = this.amount();
      }

      this.stack.setAmount(this.amount() - drained);
      if (this.amount() <= 0) {
        this.stack = null;
      }
      return drained;
    }

    return 0;
  }
}