package herbarium.common.core.commentarium;

import herbarium.api.commentarium.IPage;
import herbarium.api.commentarium.IPageLocation;
import net.minecraft.util.BlockPos;

public final class DefaultPageLocation
implements IPageLocation{
    private final IPage page;
    private final String description;
    private final BlockPos position;

    protected DefaultPageLocation(PageLocationBuilder builder){
        this.page = builder.page;
        this.description = builder.description;
        this.position = builder.pos;
    }

    @Override
    public IPage page() {
        return this.page;
    }

    @Override
    public String description() {
        return this.description;
    }

    @Override
    public BlockPos exactLocation() {
        return this.position;
    }
}