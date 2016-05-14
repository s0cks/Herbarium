package herbarium.client.font;

import com.google.common.collect.ImmutableMap;
import herbarium.api.IHerbariumFontRenderer;
import herbarium.common.Herbarium;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.Point;

public final class HerbariumFontRenderer
implements IHerbariumFontRenderer{
    private static final ImmutableMap<Character, Point> charMap;
    static{
        ImmutableMap.Builder<Character, Point> builder = ImmutableMap.builder();
        int x = 0;
        int y = 0;
        char current = 'A';
        do{
            builder.put(current, new Point(x, y));
            x += 8;
            if(x >= 128){
                x = 0;
                y += 8;
            }
            current++;
        } while(current <= 'Z');

        current = 'a';
        y = 16;
        x = 0;
        do{
            builder.put(current, new Point(x, y));
            x += 8;
            if(x >= 128){
                x = 0;
                y += 8;
            }
            current++;
        } while(current <= 'z');

        charMap = builder.build();
    }

    private static final ResourceLocation font = new ResourceLocation("herbarium", "textures/gui/font.png");
    private int posX;
    private int posY;

    @Override
    public void drawString(String str, int x, int y, int color) {
        this.posX = x;
        this.posY = y;
        GlStateManager.pushMatrix();
        for(int i = 0; i < str.length(); i++){
            this.posX += this.renderChar(str.charAt(i));
        }
        GlStateManager.popMatrix();
    }

    private float renderChar(char ch){
        Herbarium.proxy.getClient().renderEngine.bindTexture(font);
        Point p = charMap.get(ch);
        if(p != null){
            Tessellator tess = Tessellator.getInstance();
            VertexBuffer vb = tess.getBuffer();

            vb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
            vb.pos(this.posX, this.posY, 0.0F)
                    .tex(p.x, p.y)
                    .endVertex();
            vb.pos(this.posX, this.posY + 8, 0.0F)
                    .tex(p.x, p.y + 8)
                    .endVertex();
            vb.pos(this.posX + 8, this.posY + 8, 0.0F)
                    .tex(p.x + 8, p.y + 8)
                    .endVertex();
            vb.pos(this.posX + 8, this.posY, 0.0F)
                    .tex(p.x + 8, p.y)
                    .endVertex();
            tess.draw();
        }
        return 8;
    }
}