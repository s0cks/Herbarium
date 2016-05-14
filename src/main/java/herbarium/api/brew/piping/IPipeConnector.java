package herbarium.api.brew.piping;

import net.minecraft.util.math.BlockPos;

public interface IPipeConnector
    extends Iterable<PipeConnection> {
  public BlockPos position();

  public void add(BlockPos pos);

  public void remove(BlockPos pos);

  public boolean hasConnectionTo(BlockPos pos);

  public PipeConnection create(BlockPos other);
}