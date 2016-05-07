package herbarium.client.md;

import herbarium.common.Herbarium;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public final class MarkdownComponentImage
extends AbstractMarkdownComponent{
    private final String url;
    private ResourceLocation texture;
    private int width;
    private int height;
    private boolean init;

    public MarkdownComponentImage(String url, Point p){
        super(p);
        this.url = url;
    }

    public int getHeight(){
        return this.height;
    }

    private void init(){
        if(this.init) return;
        Minecraft mc = Herbarium.proxy.getClient();
        try(InputStream stream = mc.getResourceManager().getResource(new ResourceLocation("herbarium", "textures/images/" + this.url)).getInputStream()) {
            BufferedImage image = ImageIO.read(stream);
            this.width = image.getWidth();
            this.height = image.getHeight();
            this.texture = mc.getTextureManager().getDynamicTextureLocation(this.url.substring(this.url.lastIndexOf("/") + 1, this.url.lastIndexOf(".")), new DynamicTexture(image));
        } catch(Exception e){
            throw new RuntimeException("Exception loading: herbarium:textures/images/" + this.url, e);
        }
        this.init = true;
    }

    @Override
    public boolean contains(int x, int y) {
        return false;
    }

    @Override
    public void render(int x, int y, float partial) {
        this.init();
        GlStateManager.pushMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Herbarium.proxy.getClient().renderEngine.bindTexture(this.texture);
        Tessellator tess = Tessellator.getInstance();
        VertexBuffer vb = tess.getBuffer();
        vb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        vb.pos(x, y, 0)
                .tex(0.0, 0.0)
                .endVertex();
        vb.pos(x, y + this.height, 0.0)
                .tex(0.0, 1.0)
                .endVertex();
        vb.pos(x + this.width, y + this.height, 0.0)
                .tex(1.0, 1.0)
                .endVertex();
        vb.pos(x + this.width, y, 0.0)
                .tex(1.0, 0.0)
                .endVertex();
        tess.draw();
        GlStateManager.disableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.popMatrix();
    }
}