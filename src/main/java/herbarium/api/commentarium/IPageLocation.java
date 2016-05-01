package herbarium.api.commentarium;

import net.minecraft.util.BlockPos;

public interface IPageLocation{
    public IPage page();
    public String description();
    public BlockPos exactLocation();
}