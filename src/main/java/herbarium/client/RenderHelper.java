package herbarium.client;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
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

    public static void renderWireframe(BlockPos pos, int color){
        GL11.glPushMatrix();
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glLineWidth(1.0F);
        GL11.glPushMatrix();
        GL11.glTranslated(pos.getX(), pos.getY(), pos.getZ());

        int red = (color >> 16 & 0xFF);
        int green = (color >> 8 & 0xFF);
        int blue = (color & 0xFF);
        double minX = 0;
        double minY = 0;
        double minZ = 0;
        double maxX = 1;
        double maxY = 1;
        double maxZ = 1;

        Tessellator tess = Tessellator.getInstance();
        VertexBuffer vb = tess.getBuffer();

        GL11.glColor4d(red, green, blue, 255);
        vb.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        vb.pos(minX, minY, minZ).color(red, green, blue, 255).endVertex();
        vb.pos(minX, maxY, minZ).color(red, green, blue, 255).endVertex();
        vb.pos(minX, minY, maxZ).color(red, green, blue, 255).endVertex();
        vb.pos(minX, maxY, maxZ).color(red, green, blue, 255).endVertex();
    
        vb.pos(maxX, minY, minZ).color(red, green, blue, 255).endVertex();
        vb.pos(maxX, maxY, minZ).color(red, green, blue, 255).endVertex();
        vb.pos(maxX, minY, maxZ).color(red, green, blue, 255).endVertex();
        vb.pos(maxX, maxY, maxZ).color(red, green, blue, 255).endVertex();
    
        vb.pos(minX, minY, minZ).color(red, green, blue, 255).endVertex();
        vb.pos(maxX, minY, minZ).color(red, green, blue, 255).endVertex();
        vb.pos(minX, minY, maxZ).color(red, green, blue, 255).endVertex();
        vb.pos(maxX, minY, maxZ).color(red, green, blue, 255).endVertex();
    
        vb.pos(minX, maxY, minZ).color(red, green, blue, 255).endVertex();
        vb.pos(maxX, maxY, minZ).color(red, green, blue, 255).endVertex();
        vb.pos(minX, maxY, maxZ).color(red, green, blue, 255).endVertex();
        vb.pos(maxX, maxY, maxZ).color(red, green, blue, 255).endVertex();
    
        vb.pos(minX, minY, minZ).color(red, green, blue, 255).endVertex();
        vb.pos(minX, minY, maxZ).color(red, green, blue, 255).endVertex();
        vb.pos(maxX, minY, minZ).color(red, green, blue, 255).endVertex();
        vb.pos(maxX, minY, maxZ).color(red, green, blue, 255).endVertex();
    
        vb.pos(minX, maxY, minZ).color(red, green, blue, 255).endVertex();
        vb.pos(minX, maxY, maxZ).color(red, green, blue, 255).endVertex();
        vb.pos(maxX, maxY, minZ).color(red, green, blue, 255).endVertex();
        vb.pos(maxX, maxY, maxZ).color(red, green, blue, 255).endVertex();
        tess.draw();

        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
    }
}