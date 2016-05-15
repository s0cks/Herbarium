package herbarium.common.core.brew.piping;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class BrewPipingHelper {
  public static TileEntity getConnected(World world, BlockPos pos, EnumFacing dir) {
    TileEntity tile = world.getTileEntity(pos.offset(dir));
    return null;
  }
}