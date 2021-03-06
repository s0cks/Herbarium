package herbarium.client;

import herbarium.common.Herbarium;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

import java.awt.Rectangle;

public final class RenderHelper {
  public static final float TAU = (float) (Math.PI * 2.0F);
  public static final int MAX_CIRCLE_SEGMENTS = 30;
  public static final float WHOLE = 16.0F;
  public static final float UNIT = 1.0F / WHOLE;
  
  private static final float MAX_UV = 1;
  private static final float MIN_UV = 0;
  
  private RenderHelper() {}
  
  public static void translateToEntityCoords(Entity e, float partial) {
    double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * partial;
    double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * partial;
    double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * partial;
    GlStateManager.translate(-x, -y, -z);
  }
  
  public static void color(int color, int alpha) {
    int r = (color >> 16 & 0xFF);
    int g = (color >> 8 & 0xFF);
    int b = (color & 0xFF);
    GL11.glColor4d(r, g, b, alpha);
  }

  public static void renderItem(ItemStack stack) {
    if (stack == null) return;
    GlStateManager.pushMatrix();
    GlStateManager.disableLighting();
    GlStateManager.scale(0.5F, 0.5F, 0.5F);

    RenderItem render = Herbarium.proxy
                        .getClient()
                        .getRenderItem();

    if (!render.shouldRenderItemIn3D(stack)) {
      GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
    }

    GlStateManager.pushAttrib();
    net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
    render.renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
    net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
    GlStateManager.popAttrib();

    GlStateManager.enableLighting();
    GlStateManager.popMatrix();
  }
  
  public static void color(int color) {
    color(color, 255);
  }
  
  public static void renderColoredQuad(Rectangle rect, int color) {
    renderColoredQuad(rect.x, rect.y, rect.width, rect.height, color);
  }
  
  public static void renderColoredQuad(int x, int y, int width, int height, int color) {
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
  
  public static void renderCircle(float x, float y, float radius) {
    GlStateManager.glBegin(GL11.GL_TRIANGLE_FAN);
    GlStateManager.glVertex3f(x, y, 0.0F);
    for (float i = 0.0F; i <= MAX_CIRCLE_SEGMENTS; i++) {
      GlStateManager.glVertex3f(
      ((float) (x + (radius * Math.cos(i * TAU / MAX_CIRCLE_SEGMENTS)))),
      ((float) (y + (radius * Math.sin(i * TAU / MAX_CIRCLE_SEGMENTS)))),
      0.0F
      );
    }
    GlStateManager.glEnd();
  }
  
  public static void renderSemiCircle(float x, float y, float radius, float percentile) {
    int numSegments = ((int) (((percentile * 100) * MAX_CIRCLE_SEGMENTS) / 100));
    
    GlStateManager.glBegin(GL11.GL_TRIANGLE_FAN);
    GlStateManager.glVertex3f(x, y, 0.0F);
    for (float i = 0.0F; i <= numSegments; i++) {
      GlStateManager.glVertex3f(
      ((float) (x + (radius * Math.cos(i * TAU / MAX_CIRCLE_SEGMENTS)))),
      ((float) (y + (radius * Math.sin(i * TAU / MAX_CIRCLE_SEGMENTS)))),
      0.0F
      );
    }
    GlStateManager.glEnd();
  }
  
  public static void renderArc(float cx, float cy, float r, float start, float end) {
    float angle = (float) (start * Math.PI / 180.0F);
    float dAngle = (float) (end * Math.PI / (180 * 30));
    
    float lastX = (float) (cx + r * Math.cos(angle));
    float lastY = (float) (cy + r * Math.sin(angle));
    
    GlStateManager.glBegin(GL11.GL_LINES);
    for (int i = 0; i <= 30; i++, angle += dAngle) {
      float currentX = (float) (cx + r * Math.cos(angle));
      float currentY = (float) (cy + r * Math.sin(angle));
      
      GlStateManager.glVertex3f(lastX, lastY, 0.0F);
      GlStateManager.glVertex3f(currentX, currentY, 0.0F);
      
      lastX = currentX;
      lastY = currentY;
    }
    GlStateManager.glEnd();
  }
  
  public static void renderWireframe(BlockPos pos, int color) {
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
    vb.pos(minX, minY, minZ)
      .color(red, green, blue, 255)
      .endVertex();
    vb.pos(minX, maxY, minZ)
      .color(red, green, blue, 255)
      .endVertex();
    vb.pos(minX, minY, maxZ)
      .color(red, green, blue, 255)
      .endVertex();
    vb.pos(minX, maxY, maxZ)
      .color(red, green, blue, 255)
      .endVertex();
    
    vb.pos(maxX, minY, minZ)
      .color(red, green, blue, 255)
      .endVertex();
    vb.pos(maxX, maxY, minZ)
      .color(red, green, blue, 255)
      .endVertex();
    vb.pos(maxX, minY, maxZ)
      .color(red, green, blue, 255)
      .endVertex();
    vb.pos(maxX, maxY, maxZ)
      .color(red, green, blue, 255)
      .endVertex();
    
    vb.pos(minX, minY, minZ)
      .color(red, green, blue, 255)
      .endVertex();
    vb.pos(maxX, minY, minZ)
      .color(red, green, blue, 255)
      .endVertex();
    vb.pos(minX, minY, maxZ)
      .color(red, green, blue, 255)
      .endVertex();
    vb.pos(maxX, minY, maxZ)
      .color(red, green, blue, 255)
      .endVertex();
    
    vb.pos(minX, maxY, minZ)
      .color(red, green, blue, 255)
      .endVertex();
    vb.pos(maxX, maxY, minZ)
      .color(red, green, blue, 255)
      .endVertex();
    vb.pos(minX, maxY, maxZ)
      .color(red, green, blue, 255)
      .endVertex();
    vb.pos(maxX, maxY, maxZ)
      .color(red, green, blue, 255)
      .endVertex();
    
    vb.pos(minX, minY, minZ)
      .color(red, green, blue, 255)
      .endVertex();
    vb.pos(minX, minY, maxZ)
      .color(red, green, blue, 255)
      .endVertex();
    vb.pos(maxX, minY, minZ)
      .color(red, green, blue, 255)
      .endVertex();
    vb.pos(maxX, minY, maxZ)
      .color(red, green, blue, 255)
      .endVertex();
    
    vb.pos(minX, maxY, minZ)
      .color(red, green, blue, 255)
      .endVertex();
    vb.pos(minX, maxY, maxZ)
      .color(red, green, blue, 255)
      .endVertex();
    vb.pos(maxX, maxY, minZ)
      .color(red, green, blue, 255)
      .endVertex();
    vb.pos(maxX, maxY, maxZ)
      .color(red, green, blue, 255)
      .endVertex();
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