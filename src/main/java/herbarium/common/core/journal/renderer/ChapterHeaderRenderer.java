package herbarium.common.core.journal.renderer;

import herbarium.api.HerbariumApi;
import herbarium.api.commentarium.journal.IJournalPageRenderer;
import herbarium.client.RomanNumerals;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.translation.I18n;

public final class ChapterHeaderRenderer
implements IJournalPageRenderer {
  private final String header;

  public ChapterHeaderRenderer(String chapter, int ordinal){
    this.header = RomanNumerals.get(ordinal) + ": " + I18n.translateToLocal(chapter);
  }

  @Override
  public void render(int x, int y, float partial, boolean left) {
    GlStateManager.pushMatrix();
    HerbariumApi.FONT_RENDERER
        .drawString(this.header, x + 40, y + 75, 0x000000);
    GlStateManager.popMatrix();
  }
}