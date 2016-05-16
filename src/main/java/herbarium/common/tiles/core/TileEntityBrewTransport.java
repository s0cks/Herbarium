package herbarium.common.tiles.core;

import herbarium.api.brew.IBrew;
import net.minecraft.util.EnumFacing;

public abstract class TileEntityBrewTransport
extends TileEntityBrewContainer{
  @Override
  public boolean canInputFrom(IBrew brew, int amount, EnumFacing facing) {
    return true;
  }

  @Override
  public boolean canConnectTo(EnumFacing facing) {
    return true;
  }

  @Override
  public boolean canOutputTo(EnumFacing facing) {
    return true;
  }
}