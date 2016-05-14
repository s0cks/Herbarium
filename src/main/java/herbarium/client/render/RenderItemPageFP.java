package herbarium.client.render;

import herbarium.api.commentarium.pages.IPage;
import herbarium.common.Herbarium;
import herbarium.common.items.ItemPage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public final class RenderItemPageFP {
  private static final ResourceLocation texture = new ResourceLocation("herbarium", "textures/gui/note_gui.png");

  @SubscribeEvent
  public void doRender(RenderHandEvent e) {
    Minecraft mc = this.client();
    if (mc.thePlayer.getHeldItem(EnumHand.MAIN_HAND) == null) return;
    if (mc.thePlayer.getHeldItem(EnumHand.MAIN_HAND)
                    .getItem() instanceof ItemPage && mc.thePlayer.getHeldItemOffhand() == null && mc.gameSettings.thirdPersonView == 0) {
      e.setCanceled(true);

      Tessellator tess = Tessellator.getInstance();
      VertexBuffer rend = tess.getBuffer();
      EntityPlayerSP p = mc.thePlayer;

      float pitch = p.prevRotationPitch + (p.rotationPitch - p.prevRotationPitch) * e.getPartialTicks();
      float yaw = p.prevRotationYaw + (p.rotationYaw - p.prevRotationYaw) * e.getPartialTicks();
      this.rotate(pitch, yaw);
      this.rotatePitch(p, e.getPartialTicks());

      GlStateManager.enableRescaleNormal();
      GlStateManager.pushMatrix();

      mc.getTextureManager()
        .bindTexture(p.getLocationSkin());
      RenderPlayer render = ((RenderPlayer) mc.getRenderManager().<AbstractClientPlayer>getEntityRenderObject(p));

      GlStateManager.rotate(4.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.translate(0.0F, 1.6F, 0.0F);
      GlStateManager.translate(0.0F, 0.04F, -0.72F);
      GlStateManager.translate(0.0F, -0.3F, 0.0F);
      GlStateManager.rotate(87.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.translate(0.0F, -1.0F, 0.0F);
      GlStateManager.translate(0.1F, 0.0F, 0.0F);

      GlStateManager.disableCull();
      this.renderLeftArm(p, render);
      this.renderRightArm(p, render);
      GlStateManager.enableCull();

      GlStateManager.scale(0.38F, 0.38F, 0.38F);
      GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.translate(-1.0F, -0.05F, -1.0F);
      GlStateManager.translate(0.0F, 0.0F, 0.5F);
      GlStateManager.scale(0.03F, 0.03F, 0.03F);
      mc.getTextureManager()
        .bindTexture(texture);
      GL11.glNormal3f(0.0F, 0.0F, -1.0F);

      rend.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
      rend.pos(0, 64, 0)
          .tex(0, 1)
          .endVertex();
      rend.pos(64, 64, 0)
          .tex(1, 1)
          .endVertex();
      rend.pos(64, 0, 0)
          .tex(1, 0)
          .endVertex();
      rend.pos(0, 0, 0)
          .tex(0, 0)
          .endVertex();
      tess.draw();

      IPage page = ItemPage.getPage(p.getHeldItem(EnumHand.MAIN_HAND));
      if (page != null) {
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        GlStateManager.translate(0.0F, 5.0F, -1.0F);
        page.renderer()
            .render(e.getPartialTicks());
      }

      GlStateManager.popMatrix();
      GlStateManager.disableRescaleNormal();
    }
  }

  private float cos(float pitch) {
    float f = 1.0F * pitch / 45.0F + 0.1F;
    f = MathHelper.clamp_float(f, 0.0F, 1.0F);
    f = -MathHelper.cos((float) (f * Math.PI)) * 0.5F + 0.5F;
    return f;
  }

  private void renderRightArm(EntityPlayerSP player, RenderPlayer render) {
    GlStateManager.pushMatrix();
    GlStateManager.rotate(54.0F, 0.0F, 1.0F, 0.0F);
    GlStateManager.rotate(64.0F, 1.0F, 0.0F, 0.0F);
    GlStateManager.rotate(-62.0F, 0.0F, 0.0F, 1.0F);
    GlStateManager.translate(0.25F, -0.85F, 0.75F);
    render.renderRightArm(player);
    GlStateManager.popMatrix();
  }

  private void renderLeftArm(EntityPlayerSP player, RenderPlayer render) {
    GlStateManager.pushMatrix();
    GlStateManager.rotate(92.0F, 0.0F, 1.0F, 0.0F);
    GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
    GlStateManager.rotate(41.0F, 0.0F, 0.0F, 1.0F);
    GlStateManager.translate(-0.3F, -1.1F, 0.45F);
    render.renderLeftArm(player);
    GlStateManager.popMatrix();
  }

  private void rotate(float angle, float mod) {
    GlStateManager.pushMatrix();
    GlStateManager.rotate(angle, 1.0F, 0.0F, 0.0F);
    GlStateManager.rotate(mod, 0.0F, 1.0F, 0.0F);
    GlStateManager.popMatrix();
  }

  private void rotatePitch(EntityPlayerSP player, float partial) {
    float pitch = player.prevRenderArmPitch + (player.renderArmPitch - player.prevRenderArmPitch) * partial;
    float yaw = player.prevRenderArmYaw + (player.renderArmYaw - player.prevRenderArmYaw) * partial;
    GlStateManager.rotate((player.rotationPitch - pitch) * 0.2F, 1.0F, 0.0F, 0.0F);
    GlStateManager.rotate((player.rotationYaw - yaw) * 0.1F, 0.0F, 1.0F, 0.0F);
  }

  private Minecraft client() {
    return Herbarium.proxy.getClient();
  }

  private void renderPage() {

  }
}