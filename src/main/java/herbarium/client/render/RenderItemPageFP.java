package herbarium.client.render;

import herbarium.api.commentarium.IPage;
import herbarium.common.Herbarium;
import herbarium.common.items.ItemPage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public final class RenderItemPageFP{
    private static final ResourceLocation texture = new ResourceLocation("herbarium", "textures/gui/note_gui.png");

    @SubscribeEvent
    public void doRender(RenderHandEvent e){
        Minecraft mc = this.client();
        if(mc.thePlayer.getHeldItem() == null) return;
        if(mc.thePlayer.getHeldItem().getItem() instanceof ItemPage){

            /*
            GL11.glTranslatef(1.0F, 0.75F, -1.0F);
      GL11.glRotatef(135.0F, 0.0F, -1.0F, 0.0F);

      float f3 = playersp.prevRenderArmPitch + (playersp.renderArmPitch - playersp.prevRenderArmPitch) * par1;
      float f4 = playersp.prevRenderArmYaw + (playersp.renderArmYaw - playersp.prevRenderArmYaw) * par1;
      GL11.glRotatef((playermp.field_70125_A - f3) * 0.1F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef((playermp.field_70177_z - f4) * 0.1F, 0.0F, 1.0F, 0.0F);

      float var4 = playermp.field_70127_C + (playermp.field_70125_A - playermp.field_70127_C) * par1;

      float f1 = UtilsFX.getPrevEquippedProgress(mc.entityRenderer.itemRenderer) + (UtilsFX.getEquippedProgress(mc.entityRenderer.itemRenderer) - UtilsFX.getPrevEquippedProgress(mc.entityRenderer.itemRenderer)) * par1;

      GL11.glTranslatef(-0.7F * var7, -(-0.65F * var7) + (1.0F - f1) * 1.5F, 0.9F * var7);

      GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);

      GL11.glTranslatef(0.0F, 0.0F * var7, -0.9F * var7);
      GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
      GL11.glEnable(32826);

      GL11.glPushMatrix();
      GL11.glScalef(5.0F, 5.0F, 5.0F);

      mc.renderEngine.bindTexture(mc.thePlayer.func_110306_p());
      for (int var9 = 0; var9 < 2; var9++)
      {
        int var22 = var9 * 2 - 1;
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.0F, -0.6F, 1.1F * var22);
        GL11.glRotatef(-45 * var22, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(59.0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(-65 * var22, 0.0F, 1.0F, 0.0F);
        Render var24 = RenderManager.instance.getEntityRenderObject(mc.thePlayer);
        RenderPlayer var26 = (RenderPlayer)var24;
        float var13 = 1.0F;
        GL11.glScalef(var13, var13, var13);
        var26.renderFirstPersonArm(mc.thePlayer);
        GL11.glPopMatrix();
      }
      GL11.glPopMatrix();
      GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);

      GL11.glTranslatef(0.4F, -0.4F, 0.0F);
      GL11.glEnable(32826);
      GL11.glScalef(2.0F, 2.0F, 2.0F);
             */

            e.setCanceled(true);

            Tessellator tess = Tessellator.getInstance();
            WorldRenderer rend = tess.getWorldRenderer();
            EntityPlayerSP p = mc.thePlayer;

            float pitch = p.prevRotationPitch + (p.rotationPitch - p.prevRotationPitch) * e.partialTicks;
            float yaw = p.prevRotationYaw + (p.rotationYaw - p.prevRotationYaw) * e.partialTicks;
            this.rotate(pitch, yaw);
            this.rotatePitch(p, e.partialTicks);

            GlStateManager.enableRescaleNormal();
            GlStateManager.pushMatrix();

            mc.getTextureManager().bindTexture(p.getLocationSkin());
            RenderPlayer render = ((RenderPlayer) mc.getRenderManager().<AbstractClientPlayer>getEntityRenderObject(p));

            GlStateManager.rotate(4.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, 1.6F, 0.0F);
            float f3 = cos(pitch);
            GlStateManager.translate(0.0F, 0.04F, -0.72F);
            GlStateManager.translate(0.0F, -0.3F, 0.0F);
            GlStateManager.translate(0.0F, f3 * -0.5F, 0.0F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(f3 * -85.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);

            GlStateManager.disableCull();
            this.renderLeftArm(p, render);
            this.renderRightArm(p, render);
            GlStateManager.enableCull();

            GlStateManager.scale(0.38F, 0.38F, 0.38F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.translate(-1.0F, -0.05F, -1.0F);
            GlStateManager.scale(0.03F, 0.03F, 0.03F);
            GlStateManager.rotate(30.0F, 1.0F, 0.0F, 0.0F);
            mc.getTextureManager().bindTexture(texture);
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

            IPage page = ItemPage.getPage(p.getHeldItem());
            if(page != null){
                GlStateManager.scale(0.5F, 0.5F, 0.5F);
                GlStateManager.translate(-5.0F, 10.0F, -1.0F);
                mc.fontRendererObj.drawString(page.title(), 10, 10, 0x000000);
            }

            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
        }
    }

    private float cos(float pitch){
        float f = 1.0F * pitch / 45.0F + 0.1F;
        f = MathHelper.clamp_float(f, 0.0F, 1.0F);
        f = -MathHelper.cos((float) (f * Math.PI)) * 0.5F + 0.5F;
        return f;
    }

    private void renderRightArm(EntityPlayerSP player, RenderPlayer render){
        GlStateManager.pushMatrix();
        GlStateManager.rotate(54.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(64.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(-62.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(0.25F, -0.85F, 0.75F);
        render.renderRightArm(player);
        GlStateManager.popMatrix();
    }

    private void renderLeftArm(EntityPlayerSP player, RenderPlayer render){
        GlStateManager.pushMatrix();
        GlStateManager.rotate(92.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(41.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(-0.3F, -1.1F, 0.45F);
        render.renderLeftArm(player);
        GlStateManager.popMatrix();
    }

    private void rotate(float angle, float mod){
        GlStateManager.pushMatrix();
        GlStateManager.rotate(angle, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(mod, 0.0F, 1.0F, 0.0F);
        GlStateManager.popMatrix();
    }

    private void rotatePitch(EntityPlayerSP player, float partial){
        float pitch = player.prevRenderArmPitch + (player.renderArmPitch - player.prevRenderArmPitch) * partial;
        float yaw = player.prevRenderArmYaw + (player.renderArmYaw - player.prevRenderArmYaw) * partial;
        GlStateManager.rotate((player.rotationPitch - pitch) * 0.2F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate((player.rotationYaw - yaw) * 0.1F, 0.0F, 1.0F, 0.0F);
    }

    private Minecraft client(){
        return Herbarium.proxy.getClient();
    }

    private void renderPage(){

    }
}