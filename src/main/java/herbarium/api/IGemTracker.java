package herbarium.api;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public interface IGemTracker {
  public boolean isGem(IBlockState state);

  public void register(Block block);
}