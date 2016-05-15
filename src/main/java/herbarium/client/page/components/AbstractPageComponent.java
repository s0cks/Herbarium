package herbarium.client.page.components;

import herbarium.api.HerbariumApi;
import herbarium.client.page.IPageComponent;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.awt.Dimension;
import java.awt.Rectangle;

public abstract class AbstractPageComponent
implements IPageComponent {
  protected final Rectangle bounds = new Rectangle();
  protected final Dimension preferredSize = new Dimension();

  protected void setPreferredSize(int width, int height) {
    this.preferredSize.setSize(width, height);
  }

  @Override
  public final Rectangle getGeometry() {
    return this.bounds;
  }

  @Override
  public Dimension getPreferredSize() {
    return this.preferredSize;
  }

  @Override
  public void setGeometry(int x, int y, int width, int height) {
    this.bounds.setBounds(x, y, width, height);
  }

  @Override
  public void onScrollDown() {

  }

  @Override
  public void onScrollUp() {

  }

  protected void drawString(String text, int x, int y, int color) {
    HerbariumApi.FONT_RENDERER
    .drawString(text, x, y, color);
  }

  protected void drawColoredQuad(int x, int y, int width, int height, int color) {
    int red = (color >> 24 & 0xFF);
    int green = (color >> 16 & 0xFF);
    int blue = (color >> 8 & 0xFF);
    int alpha = (color & 0xFF);

    Tessellator tess = Tessellator.getInstance();
    VertexBuffer vb = tess.getBuffer();

    GlStateManager.disableTexture2D();

    if (alpha < 255) {
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    vb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
    vb.pos(x, y + height, 1)
      .color(red, green, blue, alpha)
      .endVertex();
    vb.pos(x + width, y + height, 1)
      .color(red, green, blue, alpha)
      .endVertex();
    vb.pos(x + width, y, 1)
      .color(red, green, blue, alpha)
      .endVertex();
    vb.pos(x, y, 1)
      .color(red, green, blue, alpha)
      .endVertex();
    tess.draw();

    if (alpha < 255) {
      GlStateManager.disableBlend();
    }

    GlStateManager.enableTexture2D();
  }

  protected void drawColoredQuad(Rectangle bounds, int color) {
    int red = (color >> 24 & 0xFF);
    int green = (color >> 16 & 0xFF);
    int blue = (color >> 8 & 0xFF);
    int alpha = (color & 0xFF);

    Tessellator tess = Tessellator.getInstance();
    VertexBuffer vb = tess.getBuffer();

    GlStateManager.disableTexture2D();

    if (alpha < 255) {
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    vb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
    vb.pos(bounds.x, bounds.y + bounds.height, 1)
      .color(red, green, blue, alpha)
      .endVertex();
    vb.pos(bounds.x + bounds.width, bounds.y + bounds.height, 1)
      .color(red, green, blue, alpha)
      .endVertex();
    vb.pos(bounds.x + bounds.width, bounds.y, 1)
      .color(red, green, blue, alpha)
      .endVertex();
    vb.pos(bounds.x, bounds.y, 1)
      .color(red, green, blue, alpha)
      .endVertex();
    tess.draw();

    if (alpha < 255) {
      GlStateManager.disableBlend();
    }

    GlStateManager.enableTexture2D();
  }

  protected String wrap(String text, int len) {
    StringBuilder builder = new StringBuilder(text);
    int i = 0;
    while (i + len < builder.length() && (i = builder.lastIndexOf(" ", i + len)) != -1) {
      builder.replace(i, i + 1, "\n");
    }
    return builder.toString();
  }
}