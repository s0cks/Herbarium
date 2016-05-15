package herbarium.client.render.tile;

import herbarium.client.RenderHelper;
import herbarium.common.Herbarium;
import herbarium.common.tiles.TileEntityMortar;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import org.lwjgl.opengl.GL11;

public final class RenderTileMortar
extends TileEntitySpecialRenderer<TileEntityMortar> {
  private static final ModelResourceLocation modelLoc = new ModelResourceLocation("herbarium:mortar", "inventory");

  @Override
  public void renderTileEntityAt(TileEntityMortar te, double x, double y, double z, float partialTicks, int destroyStage) {
    GlStateManager.pushMatrix();

    GlStateManager.pushMatrix();
    GlStateManager.translate(x, y, z);
    Herbarium.proxy.getClient().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
    Herbarium.proxy.dispatcher()
                   .getBlockModelRenderer()
                   .renderModelBrightnessColor(Herbarium.proxy.modelManager()
                                                              .getModel(modelLoc), 1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.popMatrix();

    ItemStack current = te.getCurrentItem();
    if (current != null) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(x + 0.5F, y + 0.5F, z + 0.5F);
      RenderHelper.renderItem(current);
      GlStateManager.popMatrix();
    }

    float pasteLevel = te.getPasteLevel();
    if (pasteLevel > 0.0F) {
      GlStateManager.translate(x, y, z);
      GlStateManager.pushMatrix();
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

      int c = 0x00FF00;

      int red = (c >> 16 & 0xFF);
      int green = (c >> 8 & 0xFF);
      int blue = (c & 0xFF);

      Tessellator tess = Tessellator.getInstance();
      VertexBuffer renderer = tess.getBuffer();
      TextureAtlasSprite sprite = Herbarium.proxy.getClient()
                                                 .getTextureMapBlocks()
                                                 .getAtlasSprite(FluidRegistry.WATER.getStill()
                                                                                    .toString());

      GlStateManager.scale(0.45F, 1.0F, 0.45F);
      GlStateManager.translate(0.6F, -0.6F, 0.6F);
      GlStateManager.translate(0.0F, pasteLevel * 0.5F, 0.0F);
      renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);

      double u1 = sprite.getMinU();
      double v1 = sprite.getMinV();
      double u2 = sprite.getMaxU();
      double v2 = sprite.getMaxV();

      renderer.pos(0, 0.5, 0)
              .tex(u1, v1)
              .color(red, green, blue, 255)
              .endVertex();
      renderer.pos(0, 0.5, 1)
              .tex(u1, v2)
              .color(red, green, blue, 255)
              .endVertex();
      renderer.pos(1, 0.5, 1)
              .tex(u2, v2)
              .color(red, green, blue, 255)
              .endVertex();
      renderer.pos(1, 0.5, 0)
              .tex(u2, v1)
              .color(red, green, blue, 255)
              .endVertex();
      tess.draw();

      GlStateManager.disableBlend();
      GlStateManager.popMatrix();
    }

    GlStateManager.popMatrix();
  }
}