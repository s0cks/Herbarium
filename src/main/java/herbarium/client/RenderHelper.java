package herbarium.client;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

public final class RenderHelper{
    public static final float TAU = (float) (Math.PI * 2.0F);

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

    public static void renderColoredQuad(int x, int y, int width, int height, int color){
        Tessellator tess = Tessellator.getInstance();
        VertexBuffer vb = tess.getBuffer();

        int r = (color >> 16 & 0xFF);
        int g = (color >> 8 & 0xFF);
        int b = (color & 0xFF);

        vb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        vb.pos(x, y, 1.0)
                .color(r, g, b, 255)
                .endVertex();
        vb.pos(x, y + height, 1.0)
                .color(r, g, b, 255)
                .endVertex();
        vb.pos(x + width, y + height, 1.0)
                .color(r, g, b, 255)
                .endVertex();
        vb.pos(x + width, y, 1.0)
                .color(r, g, b, 255)
                .endVertex();
        tess.draw();
    }

    public static void renderCircle(float x, float y, float radius){
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        GL11.glVertex2f(x, y);
        for(float i = 0.0F; i <= 20.0F; i++){
            GL11.glVertex2f(
                    (float) (x + (radius * Math.cos(i * TAU / 20.0F))),
                    (float) (y + (radius * Math.sin(i * TAU / 20.0F)))
            );
        }
        GL11.glEnd();
    }

    public static void renderArc(float cx, float cy, float r, float start, float end){
        float angle = (float) (start * Math.PI / 180.0F);
        float dAngle = (float) (end * Math.PI / (180 * 30));

        float lastX = (float) (cx + r * Math.cos(angle));
        float lastY = (float) (cy + r * Math.sin(angle));

        Tessellator tess = Tessellator.getInstance();
        VertexBuffer vb = tess.getBuffer();
        vb.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        for(int i = 0; i <= 30; i++, angle += dAngle){
            float currentX = (float) (cx + r * Math.cos(angle));
            float currentY = (float) (cy + r * Math.sin(angle));

            vb.pos(lastX, lastY, 200.0F)
                    .color(0.0F, 0.0F, 0.0F, 0.0F)
                    .endVertex();
            vb.pos(currentX, currentY, 200.0F)
                    .color(0.0F, 0.0F, 0.0F, 0.0F)
                    .endVertex();

            lastX = currentX;
            lastY = currentY;
        }
        tess.draw();
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