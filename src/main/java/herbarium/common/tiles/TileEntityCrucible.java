package herbarium.common.tiles;

import herbarium.api.brew.IBrew;
import herbarium.common.tiles.core.TileEntityBrewTransport;
import net.minecraft.util.EnumFacing;

public final class TileEntityCrucible
extends TileEntityBrewTransport {
  @Override
  public boolean canInputFrom(IBrew brew, int amount, EnumFacing facing) {
    return false;
  }

  @Override
  public int suction(EnumFacing facing) {
    return 0;
  }

  @Override
  public int minimumSuction() {
    return 0;
  }
}