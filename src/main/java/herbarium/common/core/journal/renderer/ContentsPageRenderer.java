package herbarium.common.core.journal.renderer;

import herbarium.api.commentarium.journal.IJournalPageRenderer;
import herbarium.client.RomanNumerals;
import herbarium.common.Herbarium;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.translation.I18n;

import java.util.LinkedList;
import java.util.List;

public final class ContentsPageRenderer
    implements IJournalPageRenderer {
  public static final class ContentsData {
    public String entry;
    public final boolean isChapter;
    public final int ordinal;

    public ContentsData(String entry, boolean isChapter, int ordinal) {
      this.entry = this.buildEntry(entry, ordinal);
      this.isChapter = isChapter;
      this.ordinal = ordinal;
    }

    private String buildEntry(String entry, int ordinal) {
      String localizedName = I18n.translateToLocal(entry);
      String numeral = RomanNumerals.get(ordinal);

      String e = localizedName;
      FontRenderer fr = Herbarium.proxy.getClient().fontRendererObj;
      while((fr.getStringWidth(e) + fr.getStringWidth(numeral)) < 115){
        e += '.';
      }

      return e + numeral;
    }
  }

  private final List<ContentsData> data;

  public ContentsPageRenderer(List<ContentsData> data) {
    this.data = new LinkedList<>(data);
  }

  @Override
  public void render(int x, int y, float partial, boolean left) {
    GlStateManager.pushMatrix();

    int dY = 0;
    for (ContentsData data : this.data) {
      Herbarium.proxy.getClient()
          .fontRendererObj
          .drawString(data.entry, x + (!data.isChapter ? 10 : 0), y + (dY +=
                                                                           12), 0x000000);
    }

    GlStateManager.popMatrix();
  }
}