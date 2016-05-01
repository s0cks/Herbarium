package herbarium.common.core.commentarium;

import herbarium.api.commentarium.IPage;
import herbarium.api.commentarium.IPageLocation;
import net.minecraft.util.BlockPos;

public final class PageLocationBuilder{
    protected BlockPos pos;
    protected IPage page;
    protected String description;

    public IPageLocation build(){
        return new DefaultPageLocation(this);
    }

    protected void setDescription(String desc){
        this.description = desc;
    }

    public DescriptionBuilder newDescription(){
        return new DescriptionBuilder(this);
    }

    public PageLocationBuilder setPosition(BlockPos pos){
        this.pos = pos;
        return this;
    }

    public PageLocationBuilder setPage(IPage page){
        this.page = page;
        return this;
    }

    public static final class DescriptionBuilder{
        private final StringBuilder internal = new StringBuilder();
        private final PageLocationBuilder parent;

        protected DescriptionBuilder(PageLocationBuilder parent){
            this.parent = parent;
        }

        public PageLocationBuilder build(){
            this.parent.setDescription(this.internal.toString());
            return this.parent;
        }
    }
}