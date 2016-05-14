package herbarium.common.core.journal.renderer;

import herbarium.api.brew.effects.IEffect;
import herbarium.api.commentarium.journal.IJournalPageRenderer;
import herbarium.common.Herbarium;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.text.translation.I18n;
import org.lwjgl.opengl.GL11;

public final class EffectPageRenderer
    implements IJournalPageRenderer{
  private final IEffect[] effects;

  public EffectPageRenderer(IEffect[] effects){
    this.effects = effects;
  }

  @Override
  public void render(int x, int y, float partial, boolean left) {
    int current = 0;
    for(int i = 0; i < 2; i++){
      for(int j = 0; j < 3; j++){
        IEffect effect = this.effects[current++];

        GlStateManager.pushMatrix();
        GlStateManager.translate(15, 35, 0.0F);
        GlStateManager.pushMatrix();
        GlStateManager.scale(1.25, 1.25, 1.25);

        if(left){
          GlStateManager.translate(-30, -25, 0.0F);
        } else{
          GlStateManager.translate(-65, -25, 0.0F);
        }

        Tessellator tess = Tessellator.getInstance();
        VertexBuffer vb = tess.getBuffer();

        int iX = x + (i * 55);
        int iY = y + (j * 55);

        iY -= 5;

        Herbarium.proxy.getClient()
            .renderEngine
            .bindTexture(effect.icon());
        vb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        vb.pos(iX, iY, 1.0)
          .tex(0, 0)
          .color(1.0F, 1.0F, 1.0F, 1.0F)
          .endVertex();
        vb.pos(iX, iY + 16, 1.0)
          .tex(0, 1)
          .color(1.0F, 1.0F, 1.0F, 1.0F)
          .endVertex();
        vb.pos(iX + 16, iY + 16, 1.0)
          .tex(1, 1)
          .color(1.0F, 1.0F, 1.0F, 1.0F)
          .endVertex();
        vb.pos(iX + 16, iY, 1.0)
          .tex(1, 0)
          .color(1.0F, 1.0F, 1.0F, 1.0F)
          .endVertex();
        tess.draw();
        GlStateManager.popMatrix();

        iX += 30;
        iY += 5;

        if(left){
          GlStateManager.translate(75, 10, 0.0F);
        } else{
          GlStateManager.translate(150, 10, 0.0F);
        }

        GlStateManager.pushMatrix();
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        Herbarium.proxy.getClient()
            .fontRendererObj
            .drawString(I18n.translateToLocal(effect.uuid()), iX + (i * 75) - (i == 0 ? 12 : 0), iY + (j * 80) - 9,
                        0x000000);
        GlStateManager.popMatrix();

        GlStateManager.popMatrix();
      }
    }
  }
}