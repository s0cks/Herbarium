package herbarium.client.gui;

import herbarium.api.HerbariumApi;
import herbarium.api.commentarium.IPage;
import herbarium.api.commentarium.IPageGroup;
import herbarium.client.RomanNumerals;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class GuiJournal
extends GuiScreen{
    private static final ResourceLocation texture = new ResourceLocation("herbarium", "textures/gui/journal_gui.png");
    private static final int XSIZE = 190;
    private static final int YSIZE = 93;

    private int guiLeft;
    private int guiTop;

    public GuiJournal(EntityPlayer player) {

    }

    @Override
    public void initGui() {
        this.guiLeft = (this.width - XSIZE) / 2;
        this.guiTop = (this.height - YSIZE) / 2;

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.mc.renderEngine.bindTexture(texture);
        GlStateManager.scale(3.0F, 3.0F, 3.0F);
        this.drawTexturedModalRect(this.guiLeft - XSIZE - 15, this.guiTop - YSIZE - 25, 0, 0, XSIZE, YSIZE);
        GlStateManager.scale(0.4F, 0.4F, 0.4F);

        int x = this.guiLeft + XSIZE - 145;
        int y = this.guiTop - 80;

        for(IPageGroup group : HerbariumApi.PAGE_MANAGER.sortedGroups()){
            this.fontRendererObj.drawString(TextFormatting.UNDERLINE + RomanNumerals.get(group.ordinal() + 1) + ": " + group.localizedName(), x, y += this.fontRendererObj.FONT_HEIGHT + 7, 0x000000);
            int ordinal = 1;
            for(IPage page : group.all()){
                this.fontRendererObj.drawString(RomanNumerals.get(ordinal++).toLowerCase() + ": " + page.title(), x + 10, y += this.fontRendererObj.FONT_HEIGHT + 2, 0x000000);
            }
        }
    }
}