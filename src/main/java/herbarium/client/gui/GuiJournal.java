package herbarium.client.gui;

import herbarium.api.HerbariumApi;
import herbarium.api.commentarium.journal.IJournal;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public final class GuiJournal
    extends GuiScreen {
  private static final ResourceLocation texture = new ResourceLocation("herbarium", "textures/gui/journal_gui.png");
  private static final int xSize = 140;
  private static final int ySize = 95;
  private static final float scaleFactor = 2.5F;

  private final IJournal journal;

  public GuiJournal(EntityPlayer player) {
    this.journal = HerbariumApi.JOURNAL_FACTORY.create(player);
  }

  @Override
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    GlStateManager.pushMatrix();
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.scale(scaleFactor, scaleFactor, scaleFactor);
    int guiLeft = (int) ((this.width - (xSize * scaleFactor)) / (2 * scaleFactor));
    int guiTop = (int) ((this.height - (ySize * scaleFactor)) / (2 * scaleFactor));
    this.mc.renderEngine.bindTexture(texture);
    this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    GlStateManager.popMatrix();
  }

  @Override
  protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
  throws IOException {
    this.journal.advance();
  }

  @Override
  public void initGui() {

  }

  @Override
  public boolean doesGuiPauseGame() {
    return false;
  }
}