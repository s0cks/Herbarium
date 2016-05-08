package herbarium.client.render;

import dorkbox.tweenengine.Timeline;
import dorkbox.tweenengine.Tween;
import dorkbox.tweenengine.TweenAccessor;
import dorkbox.tweenengine.TweenManager;
import herbarium.api.HerbariumApi;
import herbarium.api.brew.effects.IEffect;
import herbarium.client.RenderHelper;
import herbarium.common.Herbarium;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

import java.util.concurrent.TimeUnit;

public final class RenderEffectTray
        implements TweenAccessor<RenderEffectTray> {
    private static final byte X = 0x0;

    private static final ResourceLocation texture = new ResourceLocation("herbarium", "textures/gui/bar.png");

    private final TweenManager manager = new TweenManager();
    private float lastSystemTime;
    private boolean showing = false;
    private double trayPosX = -64.0;

    public boolean showing() {
        return this.showing;
    }

    public void show() {
        if (this.showing) return;
        this.showing = true;
        this.buildStartTimeline()
            .start(this.manager);
    }

    public void hide() {
        if (!this.showing) return;
        this.showing = false;
        this.buildEndTimeline()
            .start(this.manager);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (e.phase == TickEvent.Phase.START && e.type == TickEvent.Type.CLIENT) {
            if (lastSystemTime > 0) {
                this.manager.update((lastSystemTime = (lastSystemTime - Minecraft.getSystemTime())));
            } else {
                this.manager.update(Minecraft.getSystemTime());
            }
        }
    }

    @SubscribeEvent
    public void onRenderTick(RenderGameOverlayEvent e) {
        if (e.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS || e.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            Minecraft mc = Herbarium.proxy.getClient();
            GlStateManager.pushMatrix();
            /*
            vb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
            vb.pos(this.trayPosX, this.trayPosY, 1.0)
              .tex(0.0, 0.0)
              .endVertex();
            vb.pos(this.trayPosX, this.trayPosY + 128, 1.0)
              .tex(0.0, 1.0)
              .endVertex();
            vb.pos(this.trayPosX + 64, this.trayPosY + 128, 1.0)
              .tex(1.0, 1.0)
              .endVertex();
            vb.pos(this.trayPosX + 64, this.trayPosY, 1.0)
              .tex(1.0, 0.0)
              .endVertex();
            */

            GlStateManager.disableTexture2D();
            RenderHelper.renderColoredQuad((int) this.trayPosX, 100 - 64, 32, 256, 0x808080);
            GlStateManager.enableTexture2D();

            GlStateManager.pushMatrix();
            GlStateManager.scale(2.0F, 2.0F, 2.0F);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            Tessellator tess = Tessellator.getInstance();
            VertexBuffer vb = tess.getBuffer();

            int y = (100 - 64) - 16 - 2;
            int x = 0;
            for (IEffect effect : HerbariumApi.EFFECT_TRACKER.getEffects(mc.thePlayer)) {
                mc.renderEngine.bindTexture(effect.icon());
                vb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
                vb.pos(this.trayPosX + x, y, 3.0)
                  .tex(0.0, 0.0)
                  .color(1.0F, 1.0F, 1.0F, 1.0F)
                  .endVertex();
                vb.pos(this.trayPosX + x, y + 16, 3.0)
                  .tex(0.0, 1.0)
                  .color(1.0F, 1.0F, 1.0F, 1.0F)
                  .endVertex();
                vb.pos(this.trayPosX + x + 16, y + 16, 3.0)
                  .tex(1.0, 1.0)
                  .color(1.0F, 1.0F, 1.0F, 1.0F)
                  .endVertex();
                vb.pos(this.trayPosX + x + 16, y, 3.0)
                  .tex(1.0, 0.0)
                  .color(1.0F, 1.0F, 1.0F, 1.0F)
                  .endVertex();
                tess.draw();

                if(this.showing){
                    GlStateManager.pushMatrix();
                    int textX = (int) (this.trayPosX + 20);
                    mc.fontRendererObj.drawString(effect.toString(), textX, y + 4, 0x808080);
                    GlStateManager.popMatrix();
                }

                y += 20;
            }
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();

            GlStateManager.popMatrix();
        }
    }

    private Timeline buildStartTimeline() {
        return Timeline.createSequence()
                       .push(Tween.set(this, RenderEffectTray.X, this)
                                  .target(-64.0F))
                       .push(Tween.to(this, RenderEffectTray.X, this, TimeUnit.SECONDS.toMillis(15))
                                  .target(0.0F));
    }

    private Timeline buildEndTimeline() {
        return Timeline.createSequence()
                       .push(Tween.to(this, RenderEffectTray.X, this, TimeUnit.SECONDS.toMillis(15))
                                  .target(-64.0F));
    }

    @Override
    public int getValues(RenderEffectTray target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case RenderEffectTray.X: {
                returnValues[0] = (float) this.trayPosX;
                return 1;
            }
            default:
                return -1;
        }
    }

    @Override
    public void setValues(RenderEffectTray target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case RenderEffectTray.X: {
                this.trayPosX = newValues[0];
                break;
            }
            default:
                break;
        }
    }
}