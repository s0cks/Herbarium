package herbarium.common.core.commentarium;

import herbarium.api.commentarium.IPage;

public final class Page
implements IPage {
    private final int ordinal;
    private final String title;

    protected Page(PageBuilder builder){
        this.ordinal = PageBuilder.ordinal;
        this.title = builder.title;
    }

    @Override
    public int ordinal() {
        return this.ordinal;
    }

    @Override
    public String title() {
        return this.title;
    }

    @Override
    public String uuid() {
        return "herbarium." + this.title;
    }
}