package herbarium.client.render;

import dorkbox.tweenengine.Timeline;
import dorkbox.tweenengine.Tween;
import dorkbox.tweenengine.TweenAccessor;
import dorkbox.tweenengine.TweenEquations;
import dorkbox.tweenengine.TweenManager;
import herbarium.api.HerbariumApi;
import herbarium.api.brew.effects.IEffect;
import herbarium.client.RenderHelper;
import herbarium.common.Herbarium;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.List;

public final class RenderEffectTray
    implements TweenAccessor<RenderEffectTray> {
  private static final byte ANGLE = 0x0;
  private static final byte OPACITY = 0x1;
  private static final ResourceLocation mc_gui = new ResourceLocation("textures/gui/icons.png");

  private final TweenManager manager = new TweenManager().syncOnStart();

  private boolean showing = false;
  private float angle;
  private float opacity;

  public boolean showing() {
    return this.showing;
  }

  public void show() {
    if (this.showing) return;
    this.showing = true;
    Timeline.createParallel()
            .push(Tween.to(this, ANGLE, this, 1.0F)
                       .ease(TweenEquations.Linear)
                       .target(360.0F))
            .push(Tween.to(this, OPACITY, this, 1.0F)
                       .ease(TweenEquations.Linear)
                       .target(1.0F))
            .start(this.manager);
  }

  public void hide() {
    if (!this.showing) return;
    this.showing = false;
    Timeline.createParallel()
            .push(Tween.to(this, ANGLE, this, 1.0F)
                       .ease(TweenEquations.Linear)
                       .target(0.0F))
            .push(Tween.to(this, OPACITY, this, 1.0F)
                       .ease(TweenEquations.Linear)
                       .target(0.0F))
            .start(this.manager);
  }

  @SubscribeEvent
  public void onRenderTick(RenderGameOverlayEvent.Post e) {
    this.manager.update();
    Minecraft mc = Herbarium.proxy.getClient();
    if (mc == null) return;
    ScaledResolution sr = e.getResolution();

    int x = (sr.getScaledWidth() - 60) / 2;
    int y = (sr.getScaledHeight() - 60) / 2;

    GlStateManager.pushMatrix();
    GL11.glEnable(GL11.GL_LINE_SMOOTH);
    GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
    GL11.glLineWidth(16.0F);
    RenderHelper.renderArc(x + 30, y + 30, 30, 0.0F, this.angle);
    GlStateManager.popMatrix();
    if (e.getType() == RenderGameOverlayEvent.ElementType.ALL) {
      GlStateManager.pushMatrix();
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

      List<IEffect> effects = HerbariumApi.EFFECT_TRACKER.getEffects(mc.thePlayer);
      if (effects == null || effects.isEmpty()) {
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        return;
      }

      GlStateManager.pushMatrix();
      Tessellator tess = Tessellator.getInstance();
      VertexBuffer vb = tess.getBuffer();

      float angle = (float) (0.0F * Math.PI / 180.0F);
      float dAngle = (float) (360.0F * Math.PI / (180.0F * effects.size()));
      for (int i = 0; i < effects.size(); i++, angle += dAngle) {
        float iX = (float) ((x + 23) + 45 * Math.cos(angle));
        float iY = (float) ((y + 23) + 45 * Math.sin(angle));
        GlStateManager.scale(1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(effects.get(i)
                                           .icon());
        vb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        vb.pos(iX, iY, 1.0)
          .tex(0, 0)
          .color(1.0F, 1.0F, 1.0F, this.opacity)
          .endVertex();
        vb.pos(iX, iY + 16, 1.0)
          .tex(0, 1)
          .color(1.0F, 1.0F, 1.0F, this.opacity)
          .endVertex();
        vb.pos(iX + 16, iY + 16, 1.0)
          .tex(1, 1)
          .color(1.0F, 1.0F, 1.0F, this.opacity)
          .endVertex();
        vb.pos(iX + 16, iY, 1.0)
          .tex(1, 0)
          .color(1.0F, 1.0F, 1.0F, this.opacity)
          .endVertex();
        tess.draw();
      }

      GlStateManager.popMatrix();

      GL11.glDisable(GL11.GL_LINE_SMOOTH);
      GlStateManager.disableBlend();
      GlStateManager.popMatrix();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }
  }

  @Override
  public int getValues(RenderEffectTray target, int tweenType, float[] returnValues) {
    switch (tweenType) {
      case ANGLE: {
        returnValues[0] = this.angle;
        return 1;
      }
      case OPACITY: {
        returnValues[0] = this.opacity;
        return 1;
      }
      default:
        return -1;
    }
  }

  @Override
  public void setValues(RenderEffectTray target, int tweenType, float[] newValues) {
    switch (tweenType) {
      case ANGLE: {
        this.angle = newValues[0];
        break;
      }
      case OPACITY: {
        this.opacity = newValues[0];
        break;
      }
      default:
        break;
    }
  }
}