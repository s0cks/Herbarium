package herbarium.client.page.layouts;

import herbarium.client.page.IPageComponent;
import herbarium.client.page.IPageLayout;
import herbarium.client.page.PageComponentContainer;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

public final class PageLayoutBorder
    implements IPageLayout {
  private final Map<Position, IPageComponent> children = new HashMap<>();

  public void add(IPageComponent comp, Position pos) {
    this.children.put(pos, comp);
  }

  private IPageComponent child(Position pos) {
    return this.children.get(pos);
  }

  @Override
  public void layout(PageComponentContainer container) {
    int var4 = 0;
    int var5 = container.getHeight();
    int var6 = 0;
    int var7 = container.getWidth();

    IPageComponent var9;
    Dimension var10;
    if ((var9 = this.child(Position.TOP)) != null) {
      var9.getGeometry()
          .setSize(var7 - var6, var9.getGeometry().height);
      var10 = var9.getPreferredSize();
      var9.setGeometry(var6, var4, var7 - var6, var10.height);
      var4 += var10.height + 1;
    }

    if ((var9 = this.child(Position.BOTTOM)) != null) {
      var9.getGeometry()
          .setSize(var7 - var6, var9.getGeometry().height);
      var10 = var9.getPreferredSize();
      var9.setGeometry(var6, var5 - var10.height, var7 - var6, var10.height);
      var5 -= var10.height + 1;
    }

    if ((var9 = this.child(Position.RIGHT)) != null) {
      var9.getGeometry()
          .setSize(var9.getGeometry().width, var5 - var4);
      var10 = var9.getPreferredSize();
      var9.setGeometry(var7 - var10.width, var4, var10.width, var5 - var4);
      var7 -= var10.width + 1;
    }

    if ((var9 = this.child(Position.LEFT)) != null) {
      var9.getGeometry()
          .setSize(var9.getGeometry().width, var5 - var4);
      var10 = var9.getPreferredSize();
      var9.setGeometry(var6, var4, var10.width, var5 - var4);
      var6 += var10.width + 1;
    }

    if ((var9 = this.child(Position.CENTER)) != null) {
      var9.setGeometry(var6, var4, var7 - var6, var5 - var4);
    }
  }

  public enum Position {
    LEFT,
    RIGHT,
    TOP,
    BOTTOM,
    CENTER
  }
}