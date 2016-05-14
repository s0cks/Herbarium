package herbarium.common.core.journal.renderer;

import herbarium.api.HerbariumApi;
import herbarium.api.commentarium.journal.IJournalPageRenderer;
import net.minecraft.client.renderer.GlStateManager;

public final class TitlePageRenderer
implements IJournalPageRenderer{
  @Override
  public void render(int x, int y, float partial, boolean left) {
    GlStateManager.pushMatrix();
    GlStateManager.scale(0.9F, 0.9F, 0.9F);
    HerbariumApi.FONT_RENDERER.drawString("Herba Commentarium", x + (left ? 20 : 35), y + 10, 0x000000);
    GlStateManager.popMatrix();
  }
}