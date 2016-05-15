package herbarium.common.core.journal.renderer;

import herbarium.api.HerbariumApi;
import herbarium.api.commentarium.journal.IJournalPageRenderer;
import herbarium.client.RomanNumerals;
import herbarium.client.gui.GuiJournal;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.translation.I18n;

public final class ChapterHeaderRenderer
implements IJournalPageRenderer {
  private final String header;

  public ChapterHeaderRenderer(String chapter, int ordinal) {
    this.header = RomanNumerals.get(ordinal) + ": " + I18n.translateToLocal(chapter);
  }

  @Override
  public void render(float scaleFactor, int x, int y, float partial, boolean left) {
    GlStateManager.pushMatrix();
    scaleFactor *= 0.15F;
    GlStateManager.scale(scaleFactor, scaleFactor, scaleFactor);

    float width = HerbariumApi.FONT_RENDERER
                  .stringWidth(this.header);
    float xSize = GuiJournal.xSize / 2;
    int i = ((int) ((xSize - (width * scaleFactor)) / (2 * scaleFactor)));
    int j = ((int) ((GuiJournal.ySize - (8 * scaleFactor)) / (2 * scaleFactor)));

    HerbariumApi.FONT_RENDERER
    .drawString(this.header, x + i + (left
                                      ? 10
                                      : 5), y + j - 16, 0x000000);
    GlStateManager.popMatrix();
  }
}