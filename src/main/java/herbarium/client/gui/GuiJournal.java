package herbarium.client.gui;

import herbarium.api.HerbariumApi;
import herbarium.api.commentarium.journal.IJournal;
import herbarium.api.commentarium.journal.IJournalPage;
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
  private static final int XSIZE = 190;
  private static final int YSIZE = 93;

  private final IJournal journal;
  private int guiLeft;
  private int guiTop;

  public GuiJournal(EntityPlayer player) {
    this.journal = HerbariumApi.JOURNAL_FACTORY.create(player);
  }

  @Override
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    this.mc.renderEngine.bindTexture(texture);
    GlStateManager.scale(3.0F, 3.0F, 3.0F);
    this.drawTexturedModalRect(this.guiLeft - XSIZE - 15, this.guiTop - YSIZE - 25, 0, 0, XSIZE, YSIZE);
    GlStateManager.scale(0.4F, 0.4F, 0.4F);

    int x = this.guiLeft + XSIZE - 145;
    int y = this.guiTop - 80;

    IJournalPage left = journal.left();
    if(left != null){
      left.delegate().render(this.guiLeft - 120, y, partialTicks, true);
    }

    IJournalPage right = journal.right();
    if(right != null){
      right.delegate().render(x, y, partialTicks, false);
    }
  }

  @Override
  protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
  throws IOException {
    this.journal.advance();
  }

  @Override
  public void initGui() {
    this.guiLeft = (this.width - XSIZE) / 2;
    this.guiTop = (this.height - YSIZE) / 2;
  }
}