package herbarium.common.tiles.core;

import herbarium.api.brew.IBrew;
import herbarium.api.brew.piping.IBrewStack;
import herbarium.api.brew.piping.IBrewTransport;
import herbarium.common.core.brew.piping.BrewStack;
import net.minecraft.util.EnumFacing;

public abstract class TileEntityBrewContainer
extends TileEntityBrewSource
implements IBrewTransport {
  protected final int maxAmount;

  protected TileEntityBrewContainer(int maxAmount){
    this.maxAmount = maxAmount;
  }

  protected TileEntityBrewContainer(){
    this(IBrewStack.MAX_AMOUNT);
  }

  @Override
  public int add(IBrew brew, int amount, EnumFacing facing) {
    if(brew == null) return 0;
    if(!this.canInputFrom(brew, amount, facing)){
      return 0;
    }

    if(this.stack == null){
      return (this.stack = new BrewStack(brew, amount)).amount();
    }

    if(!BrewStack.brewsEqual(this.stack.brew(), brew)){
      return 0;
    }

    int filled = this.maxAmount - this.amount();
    if(amount < filled){
      this.stack.setAmount(this.amount() + amount);
      filled = amount;
    } else{
      this.stack.setAmount(this.maxAmount);
    }

    return filled;
  }
}