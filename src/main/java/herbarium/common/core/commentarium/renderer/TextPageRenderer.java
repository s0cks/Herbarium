package herbarium.common.core.commentarium.renderer;

import herbarium.api.commentarium.IPageRenderer;
import herbarium.common.Herbarium;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;

public final class TextPageRenderer
implements IPageRenderer{
    private final String text;

    public TextPageRenderer(ResourceLocation loc){
        try(InputStream is = Herbarium.proxy.getClient().getResourceManager().getResource(loc).getInputStream()){
            this.text = IOUtils.toString(is);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    //TODO: Custom font rendering needs to disable cull and then re-enable it
    @Override
    public void render(float partialTicks) {
        Minecraft mc = Herbarium.proxy.getClient();
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        GlStateManager.translate(60.0F, 20.0F, 0.0F);

        String[] text = this.wrap(this.text, 35).split("\n");
        for(int i = 0; i < text.length; i++){
            GlStateManager.pushMatrix();
            mc.fontRendererObj.drawString(text[i], 0, (i + 1) * mc.fontRendererObj.FONT_HEIGHT, 0x000000);
            GlStateManager.popMatrix();
        }
        GlStateManager.popMatrix();
    }

    private String wrap(String text, int len){
        StringBuilder builder = new StringBuilder(text);
        int i = 0;
        while (i + len < builder.length() && (i = builder.lastIndexOf(" ", i + len)) != -1) {
            builder.replace(i, i + 1, "\n");
        }
        return builder.toString();
    }
}