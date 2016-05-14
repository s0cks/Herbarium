package herbarium.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class GuiJournal
    extends GuiScreen {
  private static final ResourceLocation texture = new ResourceLocation("herbarium", "textures/gui/journal_gui.png");
  private static final int XSIZE = 190;
  private static final int YSIZE = 93;

  private int guiLeft;
  private int guiTop;

  public GuiJournal(EntityPlayer player) {

  }

  @Override
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    this.mc.renderEngine.bindTexture(texture);
    GlStateManager.scale(3.0F, 3.0F, 3.0F);
    this.drawTexturedModalRect(this.guiLeft - XSIZE - 15, this.guiTop - YSIZE - 25, 0, 0, XSIZE, YSIZE);
    GlStateManager.scale(0.4F, 0.4F, 0.4F);

    int x = this.guiLeft + XSIZE - 145;
    int y = this.guiTop - 80;


  }

  @Override
  public void initGui() {
    this.guiLeft = (this.width - XSIZE) / 2;
    this.guiTop = (this.height - YSIZE) / 2;

  }
}