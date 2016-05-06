package herbarium.api.commentarium;

public interface IPage{
    public int ordinal();
    public String title();
    public String uuid();
    public String description();
    public IPageRenderer renderer();
    public IPageGroup group();
}