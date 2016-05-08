package herbarium.client;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public final class RenderHelper{
    private RenderHelper(){}

    public static void translateToEntityCoords(Entity e, float partial){
        double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * partial;
        double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * partial;
        double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * partial;
        GlStateManager.translate(-x, -y, -z);
    }

    public static void color(int color, int alpha){
        int r = (color >> 16 & 0xFF);
        int g = (color >> 8 & 0xFF);
        int b = (color & 0xFF);
        GL11.glColor4d(r, g, b, alpha);
    }

    public static void color(int color){
        color(color, 255);
    }
}