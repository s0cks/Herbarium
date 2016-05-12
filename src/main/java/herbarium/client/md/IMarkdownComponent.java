package herbarium.client.md;

import java.awt.Rectangle;

public interface IMarkdownComponent{
    public Rectangle getGeometry();
    public void setGeometry(int x, int y, int width, int height);
    public void onScrollDown();
    public void onScrollUp();
    public void render(int x, int y, float partial);
}