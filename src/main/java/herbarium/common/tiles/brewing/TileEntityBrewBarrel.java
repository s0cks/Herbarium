package herbarium.common.tiles.brewing;

import herbarium.api.brew.IBrew;
import herbarium.api.brew.effects.SuctionConstants;
import herbarium.api.brew.piping.IBrewTransport;
import herbarium.common.core.brew.piping.BrewPipingHelper;
import herbarium.common.tiles.core.TileEntityBrewTransport;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public final class TileEntityBrewBarrel
extends TileEntityBrewTransport
implements ITickable {
  @Override
  public void update() {
    if (!this.getWorld().isRemote && (this.amount() < this.maxAmount)) {
      TileEntity tile = BrewPipingHelper.getConnected(this.getWorld(), this.getPos(), EnumFacing.UP);
      if (tile != null) {
        IBrewTransport transport = ((IBrewTransport) tile);
        if (!transport.canOutputTo(EnumFacing.DOWN)) {
          return;
        }

        IBrew brew = null;
        if ((this.brew() != null) && (this.amount() > 0)) {
          brew = this.brew();
        } else if ((transport.amount() > 0) && (transport.suction(EnumFacing.DOWN) < this.suction(EnumFacing.UP)) && (this.suction(EnumFacing.UP) >= transport.minimumSuction())) {
          brew = transport.brew();
        }

        if ((brew != null) && (transport.suction(EnumFacing.DOWN) < this.suction(EnumFacing.UP))) {
          this.add(brew, transport.extract(brew, 1, EnumFacing.DOWN), EnumFacing.UP);
        }
      }
    }
  }

  @Override
  public int suction(EnumFacing facing) {
    if (this.amount() < this.maxAmount) {
      return SuctionConstants.CONSUMER_SUCTION;
    }

    return SuctionConstants.NO_SUCTION;
  }

  @Override
  public int minimumSuction() {
    return SuctionConstants.CONSUMER_SUCTION;
  }

  @Override
  public boolean canInputFrom(IBrew brew, int amount, EnumFacing facing) {
    return facing == EnumFacing.UP;
  }

  @Override
  public boolean canConnectTo(EnumFacing facing) {
    return facing == EnumFacing.UP;
  }

  @Override
  public boolean canOutputTo(EnumFacing facing) {
    return facing == EnumFacing.WEST
           || facing == EnumFacing.EAST
           || facing == EnumFacing.NORTH
           || facing == EnumFacing.SOUTH;
  }
}