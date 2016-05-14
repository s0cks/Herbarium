package herbarium.client.font;

import herbarium.api.IHerbariumFontRenderer;
import herbarium.common.Herbarium;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

@Deprecated
public final class HerbariumFontRenderer
    implements IHerbariumFontRenderer {
  private static final ResourceLocation font = new ResourceLocation("herbarium", "textures/gui/font.png");

  private int posX;
  private int posY;

  @Override
  public void drawString(String str, int x, int y, int color) {
    float red = (float)(color >> 16 & 255) / 255.0F;
    float blue = (float)(color >> 8 & 255) / 255.0F;
    float green = (float)(color & 255) / 255.0F;
    this.posX = x;
    this.posY = y;
    GlStateManager.pushMatrix();
    GlStateManager.color(red, green, blue, 1.0F);
    GlStateManager.enableAlpha();
    for (int i = 0; i < str.length(); i++) {
      this.posX += this.renderChar(str.charAt(i));
    }
    GlStateManager.disableAlpha();
    GlStateManager.popMatrix();
  }

  @Override
  public float stringWidth(String str) {
    return str.length() * 8;
  }

  private float renderChar(char ch) {
    Herbarium.proxy.getClient().renderEngine.bindTexture(font);

    int i = ch % 16 * 8;
    int j = ch / 16 * 8;
    int l = 8;
    float f = (float) l - 0.01F;
    GlStateManager.glBegin(5);
    GlStateManager.glTexCoord2f((float) i / 128.0F, (float) j / 128.0F);
    GlStateManager.glVertex3f(this.posX, this.posY, 0.0F);
    GlStateManager.glTexCoord2f((float) i / 128.0F, ((float) j + 7.99F) / 128.0F);
    GlStateManager.glVertex3f(this.posX, this.posY + 7.99F, 0.0F);
    GlStateManager.glTexCoord2f(((float) i + f - 1.0F) / 128.0F, (float) j / 128.0F);
    GlStateManager.glVertex3f(this.posX + f - 1.0F, this.posY, 0.0F);
    GlStateManager.glTexCoord2f(((float) i + f - 1.0F) / 128.0F, ((float) j + 7.99F) / 128.0F);
    GlStateManager.glVertex3f(this.posX + f - 1.0F, this.posY + 7.99F, 0.0F);
    GlStateManager.glEnd();

    return 8;
  }
}