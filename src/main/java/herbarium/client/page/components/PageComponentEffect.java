package herbarium.client.page.components;

import herbarium.api.brew.effects.IEffect;
import herbarium.common.Herbarium;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public final class PageComponentEffect
extends AbstractPageComponent{
    private final String text;
    private final String uuid;
    private final BufferedImage image;
    private boolean init;
    private ResourceLocation texture;

    public PageComponentEffect(IEffect effect, String text){
        this.text = text;
        this.uuid = effect.uuid();
        try(InputStream is = Herbarium.proxy.getClient().getResourceManager().getResource(effect.icon()).getInputStream()){
            this.image = ImageIO.read(is);
            this.setPreferredSize(128, 64);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    private void init(){
        if(this.init) return;
        this.texture = Herbarium.proxy.getClient().getTextureManager().getDynamicTextureLocation(this.uuid, new DynamicTexture(this.image));
        this.init = true;
    }

    @Override
    public void render(int x, int y, float partial) {
        this.init();
        GlStateManager.pushMatrix();

        int imgWidth = (this.bounds.width - this.image.getWidth()) / 4;
        int imgHeight = (this.bounds.height - this.image.getHeight()) / 4;

        int pad = 4;
        int dX = imgWidth + (pad * 2);
        int dY = pad;
        int startX = 0;

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Herbarium.proxy.getClient().renderEngine.bindTexture(this.texture);

        Tessellator tess = Tessellator.getInstance();
        VertexBuffer vb = tess.getBuffer();

        vb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        vb.pos(x + this.bounds.x + (pad * 2), y + this.bounds.y + pad + 10, 0)
                .tex(0.0, 0.0)
                .endVertex();
        vb.pos(x + this.bounds.x + (pad * 2), y + this.bounds.y + pad + 10 + imgWidth, 0)
                .tex(0.0, 1.0)
                .endVertex();
        vb.pos(x + this.bounds.x + (pad * 2) + imgWidth, y + this.bounds.y + pad + 10 + imgHeight, 0)
                .tex(1.0, 1.0)
                .endVertex();
        vb.pos(x + this.bounds.x + (pad * 2) + imgWidth, y + this.bounds.y + pad + 10, 0)
                .tex(1.0, 0.0)
                .endVertex();
        tess.draw();
        GlStateManager.disableBlend();

        String[] lines = this.wrap(this.text, 17).split("\n");
        for (String line : lines) {
            if (dY > imgHeight) {
                break;
            }
            GlStateManager.pushMatrix();
            GlStateManager.translate(7.0F, 10.0F, 1.0F);
            GlStateManager.scale(0.75F, 0.75F, 0.75F);
            this.drawString(line, dX + pad, dY += 10, 0x000000);
            GlStateManager.popMatrix();
            startX += line.length() + 1;
        }

        dY += pad;
        dX = pad;
        lines = this.wrap(this.text.substring(startX), 25).split("\n");
        for(String line : lines){
            GlStateManager.pushMatrix();
            GlStateManager.translate(7.0F, 7.0F, 1.0F);
            GlStateManager.scale(0.75F, 0.75F, 0.75F);
            this.drawString(line, dX, dY += 10, 0x000000);
            GlStateManager.popMatrix();
            if(dY > this.bounds.height){
                break;
            }
        }

        GlStateManager.popMatrix();
    }
}