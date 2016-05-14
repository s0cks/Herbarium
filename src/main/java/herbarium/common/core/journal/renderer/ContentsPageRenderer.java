package herbarium.common.core.journal.renderer;

import herbarium.api.HerbariumApi;
import herbarium.api.IHerbariumFontRenderer;
import herbarium.api.commentarium.journal.IJournalPageRenderer;
import herbarium.client.RomanNumerals;
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
      this.entry = this.buildEntry(entry, ordinal, isChapter);
      this.isChapter = isChapter;
      this.ordinal = ordinal;
    }

    private String buildEntry(String entry, int ordinal, boolean isChapter) {
      int part;
      try{
        String last = entry.substring(entry.lastIndexOf('.') + 1);
        part = Integer.valueOf(last);
        entry = entry.substring(0, entry.lastIndexOf('.'));
      } catch(NumberFormatException e){
        part = -1;
      }

      String localizedName = I18n.translateToLocal(entry);
      if(part != -1){
        localizedName += " " + RomanNumerals.get(part);
      }

      String numeral = RomanNumerals.get(ordinal);

      String e = localizedName;
      IHerbariumFontRenderer fr = HerbariumApi.FONT_RENDERER;
      while((fr.stringWidth(e) + fr.stringWidth(numeral)) < (isChapter ? 116 : 115)){
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
      HerbariumApi.FONT_RENDERER
          .drawString(data.entry, x + (!data.isChapter ? 10 : 0), y + (dY += 12), 0x000000);
    }

    GlStateManager.popMatrix();
  }
}