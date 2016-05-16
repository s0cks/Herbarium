package herbarium.api.brew.piping;

import herbarium.api.brew.IBrew;
import net.minecraft.util.EnumFacing;

public interface IBrewSource {
  public IBrew brew();

  public int amount();

  public int extract(IBrew brew, int amount, EnumFacing facing);

  public boolean canConnectTo(EnumFacing facing);

  public boolean canOutputTo(EnumFacing facing);
}