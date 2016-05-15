package herbarium.client.page.components;

import herbarium.common.Herbarium;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public final class PageComponentImage
extends AbstractPageComponent {
  private final String url;
  private final BufferedImage image;
  private boolean init;
  private ResourceLocation texture;

  public PageComponentImage(String url) {
    this.url = url;
    try (InputStream is = Herbarium.proxy.getClient()
                                         .getResourceManager()
                                         .getResource(new ResourceLocation("herbarium", "textures/images/" + url))
                                         .getInputStream()) {
      this.image = ImageIO.read(is);
      this.setPreferredSize(this.image.getWidth(), this.image.getHeight());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void init() {
    if (this.init) return;
    Minecraft mc = Herbarium.proxy.getClient();
    this.texture = mc.getTextureManager()
                     .getDynamicTextureLocation(this.url.substring(this.url.lastIndexOf("/") + 1, this.url.lastIndexOf(".")), new DynamicTexture(image));
    this.init = true;
  }

  @Override
  public void render(int x, int y, float partial) {
    this.init();
    GlStateManager.pushMatrix();
    GlStateManager.enableTexture2D();
    GlStateManager.enableBlend();
    GlStateManager.color(1.0F, 1.0F, 1.0F);
    GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    Herbarium.proxy.getClient().renderEngine.bindTexture(this.texture);
    Tessellator tess = Tessellator.getInstance();
    VertexBuffer vb = tess.getBuffer();
    vb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
    vb.pos(x + this.bounds.x, y + this.bounds.y, 0)
      .tex(0.0, 0.0)
      .endVertex();
    vb.pos(x + this.bounds.x, y + this.bounds.y + this.bounds.height, 0.0)
      .tex(0.0, 1.0)
      .endVertex();
    vb.pos(x + this.bounds.x + this.bounds.width, y + this.bounds.y + this.bounds.height, 0.0)
      .tex(1.0, 1.0)
      .endVertex();
    vb.pos(x + this.bounds.x + this.bounds.width, y + this.bounds.y, 0.0)
      .tex(1.0, 0.0)
      .endVertex();
    tess.draw();
    GlStateManager.disableBlend();
    GlStateManager.disableTexture2D();
    GlStateManager.popMatrix();
  }
}