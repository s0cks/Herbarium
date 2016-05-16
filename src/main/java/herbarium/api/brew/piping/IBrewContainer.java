package herbarium.api.brew.piping;

import herbarium.api.brew.IBrew;
import net.minecraft.util.EnumFacing;

public interface IBrewContainer
extends IBrewSource {
  public int add(IBrew brew, int amount, EnumFacing facing);

  public int suction(EnumFacing facing);

  public int minimumSuction();

  public boolean canInputFrom(IBrew brew, int amount, EnumFacing facing);
}