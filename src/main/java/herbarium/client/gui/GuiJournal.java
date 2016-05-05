package herbarium.client.gui;

import herbarium.api.HerbariumApi;
import herbarium.api.commentarium.IPage;
import herbarium.client.md.IMarkdownComponent;
import herbarium.client.md.MarkdownComponentContainer;
import herbarium.client.md.MarkdownRenderer;
import herbarium.common.Herbarium;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.Set;

@SideOnly(Side.CLIENT)
public final class GuiJournal
extends GuiScreen{
    private static final String[] ROMAN_NUMERALS = new String[]{
            "I", "II", "III", "IV", "V",
            "VI", "VII", "VIII", "IX", "X",
            "XI", "XII", "XIII", "XIV", "XV",
            "XVI", "XVII", "XVIII", "XIX", "XX"
    };
    private static final ResourceLocation markdown = new ResourceLocation("herbarium", "pages/Test.md");
    private static final MarkdownComponentContainer container = new MarkdownComponentContainer();

    static{
        (new MarkdownRenderer(container)).render(Herbarium.proxy.getClient(), markdown);
    }

    private static final ResourceLocation texture = new ResourceLocation("herbarium", "textures/gui/journal_gui.png");
    private static final int XSIZE = 190;
    private static final int YSIZE = 93;

    private final Set<IPage> pages;

    private int guiLeft;
    private int guiTop;

    public GuiJournal(EntityPlayer player) {
        this.pages = HerbariumApi.PAGE_TRACKER.allLearned(player);
    }

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

        int x = this.guiLeft - XSIZE + 60;
        int y = this.guiTop - YSIZE - 10;

        if (this.pages != null) {
            int count = 0;
            for (IPage page : this.pages) {
                GL11.glPushMatrix();
                GL11.glScalef(0.4F, 0.4F, 0.4F);

                String numeral = ROMAN_NUMERALS[count++];
                int newX = x + 25;
                for (int i = 1; i < numeral.length(); i++) {
                    newX -= this.fontRendererObj.getCharWidth(numeral.charAt(i));
                }

                this.fontRendererObj.drawString(numeral + ": " + page.title(), newX, (y += this.fontRendererObj.FONT_HEIGHT) + 20, 0x000000);
                GL11.glScalef(1.0F, 1.0F, 1.0F);
                GL11.glPopMatrix();
            }
        }

        GlStateManager.translate(this.guiLeft - XSIZE + 60, this.guiTop - YSIZE - 23, 1.0F);
        GlStateManager.scale(0.3F, 0.3F, 0.3F);
        for (IMarkdownComponent comp : container) {
            comp.render(0, 0, partialTicks);
        }
        GlStateManager.scale(1.0F, 1.0F, 1.0F);
    }
}