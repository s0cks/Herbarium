package herbarium.common.core.ruin;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class Ruin{
  public final RuinLevel[] levels;

  private Ruin(int maxY){
    this.levels = new RuinLevel[maxY];
  }

  public static Ruin map(World world, BlockPos pos, int maxX, int maxY, int maxZ){
    Ruin ruin = new Ruin(maxY - pos.getY());
    for(int y = pos.getY(), j = 0; y <= maxY; y++, j++){
      ruin.levels[j] = RuinLevel.map(world, pos, maxX, maxZ);
    }
    return ruin;
  }

  public static final class RuinLevel{
    public final IBlockState[][] level;

    private RuinLevel(int maxX, int maxZ){
      this.level = new IBlockState[maxX][maxZ];
    }

    private static RuinLevel map(World world, BlockPos pos, int maxX, int maxZ){
      RuinLevel level = new RuinLevel(maxX - pos.getX(), maxZ - pos.getZ());
      for(int x = pos.getX(), i = 0; x <= maxX; x++, i++){
        for(int z = pos.getZ(), k = 0; z <= maxZ; z++, k++){
          level.level[i][k] = world.getBlockState(new BlockPos(x, pos.getY(), z));
        }
      }

      return level;
    }
  }
}
