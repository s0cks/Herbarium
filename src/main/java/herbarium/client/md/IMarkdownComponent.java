package herbarium.client.md;

public interface IMarkdownComponent{
    public boolean contains(int x, int y);
    public int x();
    public int y();
    public void onScrollDown();
    public void onScrollUp();
    public void render(int x, int y, float partial);
}