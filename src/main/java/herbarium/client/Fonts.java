package herbarium.client;

import net.minecraft.util.ResourceLocation;
import truetyper.FontHelper;
import truetyper.FontLoader;
import truetyper.TrueTypeFont;

public enum Fonts{
    SWAGGER;

    private final TrueTypeFont font;

    Fonts() {
        this.font = FontLoader.createFont(new ResourceLocation("herbarium", "font/" + this.name().toLowerCase() + ".ttf"), 12.0F, true);
    }

    public Fonts drawString(String str, int x, int y){
        return this.drawString(str, x, y, 0xFFFFFF);
    }

    public Fonts drawString(String str, int x, int y, int color){
        int r = (color >> 16 & 0xFF);
        int g = (color >> 8 & 0xFF);
        int b = (color & 0xFF);
        FontHelper.drawString(str, (float) x, (float) y, this.font, 1.0F, 1.0F, new float[]{ r, g, b, 1.0F});
        return this;
    }

    public float getHeight(){
        return this.font.getHeight();
    }

    public float getWidth(String str){
        return this.font.getWidth(str);
    }

    public TrueTypeFont internal(){
        return this.font;
    }
}