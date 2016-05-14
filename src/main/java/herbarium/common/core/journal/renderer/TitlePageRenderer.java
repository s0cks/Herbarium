package herbarium.common.core.journal.renderer;

import herbarium.api.HerbariumApi;
import herbarium.api.commentarium.journal.IJournalPageRenderer;
import herbarium.client.gui.GuiJournal;
import net.minecraft.client.renderer.GlStateManager;

public final class TitlePageRenderer
implements IJournalPageRenderer{
  @Override
  public void render(float scaleFactor, int x, int y, float partial, boolean left) {
    GlStateManager.pushMatrix();
    scaleFactor *= 0.15F;
    GlStateManager.scale(scaleFactor, scaleFactor, scaleFactor);

    float width = HerbariumApi.FONT_RENDERER.stringWidth("Herba Commentarium");
    float xSize = GuiJournal.xSize / 2;
    int i = ((int) ((xSize - (width * scaleFactor)) / (2 * scaleFactor)));
    int j = ((int) ((GuiJournal.ySize - (8 * scaleFactor)) / (2 * scaleFactor)));

    HerbariumApi.FONT_RENDERER.drawString("Herba Commentarium", x + i + (left ? 10 : -5), y + j - 16, 0x000000);
    GlStateManager.popMatrix();
  }
}