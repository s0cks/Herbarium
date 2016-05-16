package herbarium.common.tiles.brewing;

import herbarium.api.brew.IBrew;
import herbarium.api.brew.effects.SuctionConstants;
import herbarium.api.brew.piping.IBrewTransport;
import herbarium.common.core.brew.piping.BrewPipingHelper;
import herbarium.common.core.brew.piping.BrewStack;
import herbarium.common.tiles.core.TileEntityBrewTransport;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public final class TileEntityPipe
extends TileEntityBrewTransport
implements ITickable {
  private int suction;

  @Override
  public void update() {
    this.calculateSuction();
    if (this.suction > SuctionConstants.NO_SUCTION) {
      this.equalize();
    }
  }

  private void calculateSuction() {
    this.suction = 0;
    for (EnumFacing facing : EnumFacing.VALUES) {
      if (this.canConnectTo(facing)) {
        TileEntity tile = BrewPipingHelper.getConnected(this.getWorld(), this.getPos(), facing);
        if (tile != null) {
          IBrewTransport transport = ((IBrewTransport) tile);
          if ((this.amount() > 0) && (!BrewStack.brewsEqual(this.brew(), transport.brew()))) {
            continue;
          }

          int suction = transport.suction(facing.getOpposite());
          if ((suction > SuctionConstants.NO_SUCTION) && (suction > this.suction + 1)) {
            this.suction = suction - 1;
          }
        }
      }
    }
  }

  private void equalize() {
    for (EnumFacing facing : EnumFacing.VALUES) {
      if (this.canConnectTo(facing)) {
        TileEntity tile = BrewPipingHelper.getConnected(this.getWorld(), this.getPos(), facing);
        if (tile != null) {
          IBrewTransport transport = ((IBrewTransport) tile);
          if (!transport.canOutputTo(facing.getOpposite())) {
            continue;
          }

          if (((this.brew() == null) || (BrewStack.brewsEqual(this.brew(), transport.brew()))) && (this.suction(null) > transport.suction(facing.getOpposite())) && (this.suction(null) >= transport.minimumSuction())) {
            IBrew brew = transport.brew();
            int amount = this.add(brew, transport.extract(brew, 1, facing.getOpposite()), facing);
            if (amount > 0) {
              return;
            }
          }
        }
      }
    }
  }

  @Override
  public int suction(EnumFacing facing) {
    return this.suction;
  }

  @Override
  public int minimumSuction() {
    return SuctionConstants.NO_SUCTION;
  }

  @Override
  public void readFromNBT(NBTTagCompound compound) {
    super.readFromNBT(compound);
    this.suction = compound.getInteger("Suction");
  }

  @Override
  public void writeToNBT(NBTTagCompound compound) {
    super.writeToNBT(compound);
    compound.setInteger("Suction", this.suction);
  }
}