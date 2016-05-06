package herbarium.common.core.commentarium;

import herbarium.api.commentarium.IPage;
import herbarium.api.commentarium.IPageGroup;
import herbarium.api.commentarium.IPageRenderer;
import net.minecraft.util.text.translation.I18n;

public final class Page
implements IPage {
    private final int ordinal;
    private final String title;
    private final IPageRenderer renderer;
    private final IPageGroup group;

    protected Page(PageBuilder builder){
        this.ordinal = PageBuilder.ordinal;
        this.title = builder.title;
        this.renderer = builder.renderer;
        this.group = builder.group;
    }

    @Override
    public int ordinal() {
        return this.ordinal;
    }

    @Override
    public String title() {
        return this.title;
    }

    @Override
    public String uuid() {
        return "herbarium.page." + this.title.toLowerCase();
    }

    @Override
    public String description() {
        return I18n.translateToLocal(this.uuid());
    }

    /*
    @Override
    public void render(float partialTicks) {
        Minecraft mc = Herbarium.proxy.getClient();
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.8F, 0.8F, 0.8F);

        mc.fontRendererObj.drawString(RomanNumerals.get(this.ordinal) + ": " + this.title, 15, 15, 0x000000);

        int y = 0;
        String[] text = this.wrap(this.description(), 50).split("\n");
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        GlStateManager.translate(50, 50, 2.0F);
        for(String str : text){
            mc.fontRendererObj.drawString(str, 0, y += mc.fontRendererObj.FONT_HEIGHT, 0x000000);
        }
        GlStateManager.scale(1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }
    */

    @Override
    public IPageRenderer renderer() {
        return this.renderer;
    }

    @Override
    public IPageGroup group() {
        return this.group;
    }

    @Override
    public boolean equals(Object obj){
        return obj instanceof Page
            && ((Page) obj).uuid().equals(this.uuid());
    }
}