package herbarium.client.render.tile;

import herbarium.client.HerbariumTessellator;
import herbarium.common.tiles.TileEntityRuinSelector;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

public final class RenderTileRuinSelector
extends TileEntitySpecialRenderer<TileEntityRuinSelector>{
  @Override
  public void renderTileEntityAt(TileEntityRuinSelector te, double x, double y, double z, float partialTicks, int destroyStage) {
    GlStateManager.pushMatrix();
    GlStateManager.translate(x, y, z );
    this.renderWireframe(new BlockPos(x, y, z), te.getBox(), 0x000000);
    GlStateManager.popMatrix();
  }

  private void renderWireframe(BlockPos pos, AxisAlignedBB box, int color) {
    GlStateManager.pushMatrix();
    GlStateManager.enableBlend();
    GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    GlStateManager.glLineWidth(16.0F);
    GlStateManager.pushMatrix();

    int red = (color >> 16 & 0xFF);
    int green = (color >> 8 & 0xFF);
    int blue = (color & 0xFF);
    double minX = box.minX;
    double minY = box.minY;
    double minZ = box.minZ;
    double maxX = box.maxX;
    double maxY = box.maxY;
    double maxZ = box.maxZ;

    HerbariumTessellator tess = HerbariumTessellator.instance();
    VertexBuffer vb = tess.getBuffer();

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

    GlStateManager.popMatrix();
    GlStateManager.popMatrix();
  }
}