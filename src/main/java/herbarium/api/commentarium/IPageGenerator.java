package herbarium.api.commentarium;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public interface IPageGenerator{
    public IPageManager manager();
    public IPageLocation nextLocation(World world, BlockPos last);
}