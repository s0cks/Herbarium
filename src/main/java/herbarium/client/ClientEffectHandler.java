package herbarium.client;

import herbarium.api.HerbariumApi;
import herbarium.common.Herbarium;
import herbarium.common.HerbariumConfig;
import herbarium.common.core.brew.effects.effect.RemedyEffects;
import herbarium.common.core.brew.effects.effect.SpiritEffects;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public final class ClientEffectHandler {
  @SubscribeEvent
  public void doXRayTracking(RenderWorldLastEvent e) {
    EntityPlayer player = Herbarium.proxy.getClient().thePlayer;
    if (HerbariumApi.EFFECT_TRACKER.effectActive(player, SpiritEffects.LUCKY)) {
      GL11.glPushMatrix();
      RenderHelper.translateToEntityCoords(player, e.getPartialTicks());
      GL11.glDisable(GL11.GL_TEXTURE_2D);
      GL11.glDisable(GL11.GL_LIGHTING);
      GL11.glEnable(GL11.GL_BLEND);
      GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

      World world = player.getEntityWorld();
      int range = HerbariumConfig.LUCKY_RADIUS.getInt();
      int x = (int) player.posX;
      int y = (int) player.posY;
      int z = (int) player.posZ;

      for (int i = x - range; i <= x + range; i++) {
        for (int k = z - range; k <= z + range; k++) {
          for (int j = y - range; j < y + range; j++) {
            BlockPos pos = new BlockPos(i, j, k);
            if (HerbariumApi.GEM_TRACKER.isGem(world.getBlockState(pos))) {
              GL11.glPushMatrix();
              RenderHelper.renderWireframe(pos, 0xDAA520);
              GL11.glPopMatrix();
            }
          }
        }
      }

      GL11.glEnable(GL11.GL_LIGHTING);
      GL11.glEnable(GL11.GL_TEXTURE_2D);
      GL11.glDisable(GL11.GL_BLEND);
      GL11.glPopMatrix();
    }
  }

  @SubscribeEvent
  public void doProwlerVisionTracking(RenderWorldLastEvent e) {
    EntityPlayer player = Herbarium.proxy.getClient().thePlayer;
    if (HerbariumApi.EFFECT_TRACKER.effectActive(player, RemedyEffects.PROWLER_VISION)) {
      World world = player.getEntityWorld();
      int radius = HerbariumConfig.PROWLER_VISION_RADIUS.getInt();

      GlStateManager.pushMatrix();
      RenderHelper.translateToEntityCoords(player, e.getPartialTicks());
      GL11.glDisable(GL11.GL_LIGHTING);

      for (Entity entity : world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox()
                                                                                    .expand(radius, radius, radius))) {
        if (entity instanceof EntityLiving) {
          GL11.glPushMatrix();
          GL11.glTranslated(entity.posX, entity.posY, entity.posZ);
          GL11.glEnable(GL11.GL_BLEND);
          GL11.glLineWidth(2.0F);
          GL11.glDisable(GL11.GL_TEXTURE_2D);
          GL11.glDisable(GL11.GL_CULL_FACE);
          GL11.glDepthMask(false);
          GL11.glDisable(GL11.GL_DEPTH_TEST);
          GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
          GL11.glEnable(GL11.GL_POLYGON_OFFSET_LINE);
          GL11.glPolygonOffset(-1.0F, -1.0F);
          RenderHelper.color(0xFFFFFF);
          GL11.glColorMask(true, false, true, false);
          GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
          Herbarium.proxy.getClient()
                         .getRenderManager()
                         .getEntityRenderObject(entity)
                         .doRender(entity, 0.0F, 0.0F, 0.0F, 0.0F, e.getPartialTicks());
          GL11.glColorMask(true, true, true, true);
          GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
          GL11.glEnable(GL11.GL_CULL_FACE);
          GL11.glEnable(GL11.GL_TEXTURE_2D);
          GL11.glDisable(GL11.GL_BLEND);
          GL11.glEnable(GL11.GL_DEPTH_TEST);
          GL11.glDepthMask(true);
          GL11.glPopMatrix();
        }
      }

      GL11.glEnable(GL11.GL_LIGHTING);
      GlStateManager.popMatrix();
    }
  }
}