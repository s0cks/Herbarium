package herbarium.common.core.brew.piping;

import herbarium.api.brew.piping.IBrewTransport;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class BrewPipingHelper {
  public static TileEntity getConnected(World world, BlockPos pos, EnumFacing dir) {
    TileEntity tile = world.getTileEntity(pos.offset(dir));
    if((tile instanceof IBrewTransport) && (((IBrewTransport) tile)).canConnectTo(dir.getOpposite())){
      return tile;
    }
    return null;
  }
}