package herbarium.client.page;

import java.awt.Dimension;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class PageComponentContainer
implements IPageScreen,
           Iterable<IPageComponent> {
  private final Dimension size;

  private final List<IPageComponent> components = new LinkedList<>();

  public PageComponentContainer(int width, int height) {
    this.size = new Dimension(width, height);
  }

  public int getWidth() {
    return this.size.width;
  }

  public int getHeight() {
    return this.size.height;
  }

  public int componentCount() {
    return this.components.size();
  }

  public IPageComponent componentAt(int i) {
    return this.components.get(i);
  }

  @Override
  public void register(IPageComponent comp) {
    this.components.add(comp);
  }

  @Override
  public Iterator<IPageComponent> iterator() {
    return this.components.iterator();
  }
}
