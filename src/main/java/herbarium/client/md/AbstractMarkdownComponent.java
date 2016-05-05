package herbarium.client.md;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.awt.Point;
import java.awt.Rectangle;

public abstract class AbstractMarkdownComponent
implements IMarkdownComponent{
    protected final Point position;

    protected AbstractMarkdownComponent(Point position) {
        this.position = position;
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
}