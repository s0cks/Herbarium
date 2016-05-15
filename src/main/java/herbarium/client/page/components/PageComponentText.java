package herbarium.client.page.components;

import net.minecraft.client.renderer.GlStateManager;

public final class PageComponentText
extends AbstractPageComponent {
  private final String[] lines;

  public PageComponentText(String text) {
    this.lines = this.wrap(text, 30)
                     .split("\n");
    this.setPreferredSize(10, this.lines.length * 9);
  }

  @Override
  public void render(int x, int y, float partial) {
    GlStateManager.pushMatrix();
    int dY = 0;
    for (String line : this.lines) {
      this.drawString(line, x + this.bounds.x, y + this.bounds.y + (dY += 9), 0x000000);
    }
    GlStateManager.popMatrix();
  }
}