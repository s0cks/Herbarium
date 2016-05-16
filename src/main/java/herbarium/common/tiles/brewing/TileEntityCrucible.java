package herbarium.common.tiles.brewing;

import herbarium.api.brew.IBrew;
import herbarium.api.brew.effects.SuctionConstants;
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
    return SuctionConstants.PRODUCER_SUCTION;
  }

  @Override
  public int minimumSuction() {
    return SuctionConstants.NO_SUCTION;
  }
}