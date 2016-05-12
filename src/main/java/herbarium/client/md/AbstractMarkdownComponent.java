package herbarium.client.md;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.awt.Rectangle;

public abstract class AbstractMarkdownComponent
implements IMarkdownComponent{
    protected void drawColoredQuad(int x, int y, int width, int height, int color){
        int red = (color >> 24 & 0xFF);
        int green = (color >> 16 & 0xFF);
        int blue = (color >> 8 & 0xFF);
        int alpha = (color & 0xFF);

        Tessellator tess = Tessellator.getInstance();
        VertexBuffer vb = tess.getBuffer();

        GlStateManager.disableTexture2D();

        if(alpha < 255){
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        }

        vb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        vb.pos(x, y + height, 1)
          .color(red, green, blue, alpha)
          .endVertex();
        vb.pos(x + width, y + height, 1)
          .color(red, green, blue, alpha)
          .endVertex();
        vb.pos(x + width, y, 1)
          .color(red, green, blue, alpha)
          .endVertex();
        vb.pos(x, y, 1)
          .color(red, green, blue, alpha)
          .endVertex();
        tess.draw();

        if(alpha < 255){
            GlStateManager.disableBlend();
        }

        GlStateManager.enableTexture2D();
    }

    protected void drawColoredQuad(Rectangle bounds, int color){
        int red = (color >> 24 & 0xFF);
        int green = (color >> 16 & 0xFF);
        int blue = (color >> 8 & 0xFF);
        int alpha = (color & 0xFF);

        Tessellator tess = Tessellator.getInstance();
        VertexBuffer vb = tess.getBuffer();

        GlStateManager.disableTexture2D();

        if(alpha < 255){
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        }

        vb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        vb.pos(bounds.x, bounds.y + bounds.height, 1)
                .color(red, green, blue, alpha)
                .endVertex();
        vb.pos(bounds.x + bounds.width, bounds.y + bounds.height, 1)
                .color(red, green, blue, alpha)
                .endVertex();
        vb.pos(bounds.x + bounds.width, bounds.y, 1)
                .color(red, green, blue, alpha)
                .endVertex();
        vb.pos(bounds.x, bounds.y, 1)
                .color(red, green, blue, alpha)
                .endVertex();
        tess.draw();

        if(alpha < 255){
            GlStateManager.disableBlend();
        }

        GlStateManager.enableTexture2D();
    }

    protected String wrap(String text, int len){
        StringBuilder builder = new StringBuilder(text);
        int i = 0;
        while (i + len < builder.length() && (i = builder.lastIndexOf(" ", i + len)) != -1) {
            builder.replace(i, i + 1, "\n");
        }
        return builder.toString();
    }

    @Override
    public void onScrollDown() {

    }

    @Override
    public void onScrollUp() {

    }
}