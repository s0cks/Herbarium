package herbarium.common.core.commentarium;

import herbarium.api.commentarium.IPageGroup;
import herbarium.api.commentarium.IPageRenderer;

public final class PageBuilder{
    protected static int ordinal = 0x0;

    protected IPageGroup group;
    protected String title;
    protected IPageRenderer renderer;

    public PageBuilder setTitle(String title){
        this.title = title;
        return this;
    }

    public PageBuilder setGroup(IPageGroup group){
        this.group = group;
        return this;
    }

    public PageBuilder setRenderer(IPageRenderer renderer){
        this.renderer = renderer;
        return this;
    }

    public Page build(){
        ordinal++;
        return new Page(this);
    }
}