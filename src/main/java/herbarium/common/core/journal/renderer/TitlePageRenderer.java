package herbarium.common.core.journal.renderer;

import herbarium.api.commentarium.journal.IJournalPageRenderer;
import herbarium.common.Herbarium;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public final class TitlePageRenderer
implements IJournalPageRenderer{
  @Override
  public void render(int x, int y, float partial, boolean left) {
    GlStateManager.pushMatrix();
    Minecraft mc = Herbarium.proxy.getClient();
    mc.fontRendererObj.drawString("Herbarium", x, y, 0x000000);
    GlStateManager.popMatrix();
  }
}