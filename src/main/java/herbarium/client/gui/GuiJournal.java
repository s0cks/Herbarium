package herbarium.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public final class GuiJournal
extends GuiScreen{
    private static final ResourceLocation texture = new ResourceLocation("herbarium", "textures/gui/journal_gui.png");
    private static final int XSIZE = 190;
    private static final int YSIZE = 93;

    private int guiLeft;
    private int guiTop;

    @Override
    public void initGui() {
        this.guiLeft = (this.width - XSIZE) / 2;
        this.guiTop = (this.height - YSIZE) / 2;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.mc.renderEngine.bindTexture(texture);
        GL11.glScalef(3.0F, 3.0F, 3.0F);
        this.drawTexturedModalRect(this.guiLeft - XSIZE - 15, this.guiTop - YSIZE - 25, 0, 0, XSIZE, YSIZE);
        GL11.glScalef(1.0F, 1.0F, 1.0F);
    }
}