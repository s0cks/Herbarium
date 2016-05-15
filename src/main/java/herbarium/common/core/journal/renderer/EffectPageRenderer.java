package herbarium.common.core.journal.renderer;

import herbarium.api.brew.effects.IEffect;
import herbarium.api.commentarium.journal.IJournalPageRenderer;
import herbarium.client.gui.GuiJournal;
import herbarium.common.Herbarium;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public final class EffectPageRenderer
implements IJournalPageRenderer {
  private static final ResourceLocation overlay = new ResourceLocation("herbarium", "textures/gui/entry_overlay_1.png");

  private final IEffect[] effects;

  public EffectPageRenderer(IEffect[] effects) {
    this.effects = effects;
  }

  @Override
  public void render(float scaleFactor, int x, int y, float partial, boolean left) {
    GlStateManager.pushMatrix();
    scaleFactor *= 0.175F;
    GlStateManager.scale(scaleFactor, scaleFactor, scaleFactor);

    x = (int) (GuiJournal.xSize / 2 * scaleFactor) - 10;

    int current = 0;
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 3; j++) {
        IEffect effect = this.effects[current++];

        Tessellator tess = Tessellator.getInstance();
        VertexBuffer vb = tess.getBuffer();

        int iX = x + i * 48;
        int iY = y + j * 48;

        GlStateManager.pushMatrix();
        float overlayScaleFactor = 1.5F;
        GlStateManager.scale(overlayScaleFactor, overlayScaleFactor, overlayScaleFactor);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        int tX = ((int) (iX - (i * overlayScaleFactor))) - 1;
        int tY = ((int) (iY - ((j + 1) * overlayScaleFactor) - (j * overlayScaleFactor))) + 14;

        Herbarium.proxy
        .getClient()
        .renderEngine
        .bindTexture(overlay);
        vb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        vb.pos(tX, tY, 1.0)
          .tex(0, 0)
          .color(1.0F, 1.0F, 1.0F, 1.0F)
          .endVertex();
        vb.pos(tX, tY + 18, 1.0)
          .tex(0, 1)
          .color(1.0F, 1.0F, 1.0F, 1.0F)
          .endVertex();
        vb.pos(tX + 18, tY + 18, 1.0)
          .tex(1, 1)
          .color(1.0F, 1.0F, 1.0F, 1.0F)
          .endVertex();
        vb.pos(tX + 18, tY, 1.0)
          .tex(1, 0)
          .color(1.0F, 1.0F, 1.0F, 1.0F)
          .endVertex();
        tess.draw();
        GlStateManager.disableBlend();

        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        float iconScaleFactor = 1.5F;
        GlStateManager.scale(iconScaleFactor, iconScaleFactor, iconScaleFactor);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        tX = ((int) (iX - (i * iconScaleFactor)));
        tY = ((int) (iY - ((j + 1) * iconScaleFactor) - (j * iconScaleFactor))) + 15;

        Herbarium.proxy.getClient()
        .renderEngine
        .bindTexture(effect.icon());
        vb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        vb.pos(tX, tY, 1.0)
          .tex(0, 0)
          .color(1.0F, 1.0F, 1.0F, 1.0F)
          .endVertex();
        vb.pos(tX, tY + 16, 1.0)
          .tex(0, 1)
          .color(1.0F, 1.0F, 1.0F, 1.0F)
          .endVertex();
        vb.pos(tX + 16, tY + 16, 1.0)
          .tex(1, 1)
          .color(1.0F, 1.0F, 1.0F, 1.0F)
          .endVertex();
        vb.pos(tX + 16, tY, 1.0)
          .tex(1, 0)
          .color(1.0F, 1.0F, 1.0F, 1.0F)
          .endVertex();
        tess.draw();

        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
      }
    }

    GlStateManager.popMatrix();
  }
}