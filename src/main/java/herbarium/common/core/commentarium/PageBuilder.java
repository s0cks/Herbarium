package herbarium.common.core.commentarium;

public final class PageBuilder{
    protected static int ordinal = 0x0;

    protected String title;

    public PageBuilder setTitle(String title){
        this.title = title;
        return this;
    }

    public Page build(){
        ordinal++;
        return new Page(this);
    }
}