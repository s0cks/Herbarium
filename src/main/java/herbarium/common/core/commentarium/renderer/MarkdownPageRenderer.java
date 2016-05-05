package herbarium.common.core.commentarium.renderer;

import herbarium.api.commentarium.IPageRenderer;
import herbarium.client.md.IMarkdownComponent;
import herbarium.client.md.MarkdownComponentContainer;
import herbarium.client.md.MarkdownRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public final class MarkdownPageRenderer
implements IPageRenderer{
    private final MarkdownComponentContainer container;

    public MarkdownPageRenderer(ResourceLocation loc){
        this.container = MarkdownRenderer.render(loc);
    }

    @Override
    public void render(float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        GlStateManager.translate(35.0F, 20.0F, 0.0F);
        for(IMarkdownComponent comp : container){
            comp.render(0, 0, partialTicks);
        }
        GlStateManager.popMatrix();
    }
}