package herbarium.api.brew;

import net.minecraft.util.BlockPos;

public interface IPipeConnector
extends Iterable<PipeConnection>{
    public BlockPos position();
    public void add(BlockPos pos);
    public void remove(BlockPos pos);
    public boolean hasConnectionTo(BlockPos pos);
    public PipeConnection create(BlockPos other);
}