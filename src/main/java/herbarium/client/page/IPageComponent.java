package herbarium.client.page;

import java.awt.Dimension;
import java.awt.Rectangle;

public interface IPageComponent {
  public Rectangle getGeometry();

  public Dimension getPreferredSize();

  public void setGeometry(int x, int y, int width, int height);

  public void onScrollDown();

  public void onScrollUp();

  public void render(int x, int y, float partial);
}