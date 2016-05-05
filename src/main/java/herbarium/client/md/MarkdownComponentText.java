package herbarium.client.md;

import herbarium.common.Herbarium;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class MarkdownComponentText
extends AbstractMarkdownComponent{
    private final String text;

    public MarkdownComponentText(String text, Point position) {
        super(position);
        this.text = text;
    }

    public Rectangle computeBounds(int x, int y){
        String[] split = this.wrap(this.text, 35).split("\n");
        Minecraft mc = Herbarium.proxy.getClient();
        int width = 0;
        int height = 0;
        for(String str : split){
            int newWidth = mc.fontRendererObj.getStringWidth(str);
            if(newWidth > width){
                width = newWidth;
            }
            height += mc.fontRendererObj.FONT_HEIGHT;
        }
        return new Rectangle(new Point(this.position.x + x, this.position.y + y + (mc.fontRendererObj.FONT_HEIGHT * 2)), new Dimension(width + 10, (height - mc.fontRendererObj.FONT_HEIGHT) + 20));
    }

    @Override
    public void render(int x, int y, float partial) {
        String[] split = this.wrap(this.text, 35).split("\n");
        Minecraft mc = Herbarium.proxy.getClient();
        this.drawColoredQuad(this.computeBounds(x, y), 0x000000FF);
        int drawY = 5;
        for(String str : split){
            GlStateManager.translate(0.0F, 0.0F, 100.0F);
            mc.fontRendererObj.drawString(str, x + this.position.x + 5, y + this.position.y + 10 + (drawY += mc.fontRendererObj.FONT_HEIGHT), 0xFFFFFF);
            GlStateManager.translate(0.0F, 0.0F, -100.0F);
        }
    }

    private String wrap(String text, int len){
        StringBuilder builder = new StringBuilder(text);
        int i = 0;
        while (i + len < builder.length() && (i = builder.lastIndexOf(" ", i + len)) != -1) {
            builder.replace(i, i + 1, "\n");
        }
        return builder.toString();
    }
}