package herbarium.client.md;

import herbarium.common.Herbarium;
import net.minecraft.client.Minecraft;

import java.awt.Point;

public class MarkdownComponentText
extends AbstractMarkdownComponent{
    private final String text;

    public MarkdownComponentText(String text, Point position) {
        super(position);
        this.text = text;
    }

    public int getHeight(){
        return this.wrap(this.text, 35).split("\n").length * 9;
    }

    @Override
    public boolean contains(int x, int y) {
        return false;
    }

    @Override
    public void render(int x, int y, float partial) {
        String[] split = this.wrap(this.text, 35).split("\n");
        Minecraft mc = Herbarium.proxy.getClient();
        int drawY = 5;
        for(String str : split){
            mc.fontRendererObj.drawString(str, x, y + (drawY += mc.fontRendererObj.FONT_HEIGHT), 0x000000);
        }
    }
}