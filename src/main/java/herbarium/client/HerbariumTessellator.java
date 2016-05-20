package herbarium.client;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public final class HerbariumTessellator
extends Tessellator {
  private static HerbariumTessellator instance = null;

  private HerbariumTessellator() {
    super(0x200000);
  }

  public static HerbariumTessellator instance() {
    return (instance == null
            ? (instance = new HerbariumTessellator())
            : instance);
  }

  public HerbariumTessellator renderCuboid(AxisAlignedBB box) {
    GlStateManager.pushMatrix();

    VertexBuffer vb = this.getBuffer();
    vb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

    double minX = box.minX;
    double minY = box.minY;
    double minZ = box.minZ;
    double maxX = box.maxX;
    double maxY = box.maxY;
    double maxZ = box.maxZ;

    double minU = 0.0;
    double minV = 0.0;
    double maxU = 1.0;
    double maxV = 1.0;

    vb.pos(minX, maxY, minZ)
      .tex(minU, minV)
      .endVertex();
    vb.pos(minX, maxY, maxZ)
      .tex(minU, maxV)
      .endVertex();
    vb.pos(maxX, maxY, maxZ)
      .tex(maxU, maxV)
      .endVertex();
    vb.pos(maxX, maxY, minZ)
      .tex(maxU, minV)
      .endVertex();

    vb.pos(maxX, minY, minZ)
      .tex(minU, minV)
      .endVertex();
    vb.pos(maxX, minY, maxZ)
      .tex(minU, maxV)
      .endVertex();
    vb.pos(minX, minY, maxZ)
      .tex(maxU, maxV)
      .endVertex();
    vb.pos(minX, minY, minZ)
      .tex(maxU, minV)
      .endVertex();

    vb.pos(maxX, minY, minZ)
      .tex(minU, minV)
      .endVertex();
    vb.pos(maxX, maxY, minZ)
      .tex(minU, maxV)
      .endVertex();
    vb.pos(maxX, maxY, maxZ)
      .tex(maxU, maxV)
      .endVertex();
    vb.pos(maxX, minY, maxZ)
      .tex(maxU, minV)
      .endVertex();

    vb.pos(minX, minY, minZ)
      .tex(minU, minV)
      .endVertex();
    vb.pos(minX, maxY, minZ)
      .tex(minU, maxV)
      .endVertex();
    vb.pos(maxX, maxY, minZ)
      .tex(maxU, maxV)
      .endVertex();
    vb.pos(maxX, minY, minZ)
      .tex(maxU, minV)
      .endVertex();

    vb.pos(maxX, minY, maxZ)
      .tex(minU, minV)
      .endVertex();
    vb.pos(maxX, maxY, maxZ)
      .tex(minU, maxV)
      .endVertex();
    vb.pos(minX, maxY, maxZ)
      .tex(maxU, maxV)
      .endVertex();
    vb.pos(minX, minY, maxZ)
      .tex(maxU, minV)
      .endVertex();

    vb.pos(minX, minY, maxZ)
      .tex(minU, minV)
      .endVertex();
    vb.pos(minX, maxY, maxZ)
      .tex(minU, maxV)
      .endVertex();
    vb.pos(minX, maxY, minZ)
      .tex(maxU, maxV)
      .endVertex();
    vb.pos(minX, minY, minZ)
      .tex(maxU, minV)
      .endVertex();

    this.draw();

    GlStateManager.popMatrix();
    return this;
  }
}