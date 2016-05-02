package herbarium.client.gui;

import herbarium.api.HerbariumApi;
import herbarium.api.commentarium.IPage;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.Set;

@SideOnly(Side.CLIENT)
public final class GuiJournal
extends GuiScreen{
    private static final ResourceLocation texture = new ResourceLocation("herbarium", "textures/gui/journal_gui.png");
    private static final int XSIZE = 190;
    private static final int YSIZE = 93;

    private final EntityPlayer player;

    public GuiJournal(EntityPlayer player){
        this.player = player;
    }

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

        int x = this.guiLeft - XSIZE;
        int y = this.guiTop - YSIZE - 10;
        Set<IPage> pages = HerbariumApi.PAGE_TRACKER.allLearned(player);
        if(pages != null){
            for(IPage page : pages){
                this.drawString(this.fontRendererObj, page.title(), x, y += this.fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
            }
        }
    }
}