package herbarium.common.core.commentarium;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import herbarium.api.HerbariumApi;
import herbarium.api.brew.effects.IEffect;
import herbarium.api.commentarium.IPage;
import herbarium.api.commentarium.IPageGroup;
import herbarium.api.commentarium.IPageRenderer;
import herbarium.client.page.IPageComponent;
import herbarium.client.page.IPageLayout;
import herbarium.client.page.PageComponentContainer;
import herbarium.client.page.components.PageComponentEffect;
import herbarium.client.page.components.PageComponentImage;
import herbarium.client.page.components.PageComponentText;
import herbarium.client.page.layouts.PageLayoutBorder;
import herbarium.client.page.layouts.PageLayoutGrid;
import net.minecraft.client.renderer.GlStateManager;

import java.lang.reflect.Type;

public final class JsonPageDeserializer
implements JsonDeserializer<IPage> {
    @Override
    public IPage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
    throws JsonParseException {
        if(!json.isJsonObject()) return null;
        JsonObject ctx = json.getAsJsonObject();

        String title = ctx.get("title").getAsString();
        IPageGroup group = PageGroups.valueOf(ctx.get("group").getAsString().toUpperCase());
        IPageLayout layout;
        JsonPageRenderer renderer = new JsonPageRenderer(new PageComponentContainer(192, 192));

        JsonElement layoutElem = ctx.get("layout");
        if(layoutElem.isJsonObject()){
            JsonObject layoutObj = layoutElem.getAsJsonObject();
            switch(layoutObj.get("name").getAsString().toLowerCase()){
                case "grid":{
                    int rows = layoutObj.get("rows").getAsInt();
                    int columns = layoutObj.get("columns").getAsInt();
                    layout = new PageLayoutGrid(rows, columns);
                    break;
                }
                default:{
                    throw new JsonParseException("Unknown layout type: " + layoutObj.get("name").getAsString().toLowerCase());
                }
            }
        } else if(layoutElem.isJsonPrimitive()){
            switch(layoutElem.getAsString()){
                case "border": layout = new PageLayoutBorder(); break;
                default: {
                    throw new JsonParseException("Unknown layout type: " + layoutElem.getAsString());
                }
            }
        } else{
            throw new JsonParseException("layout can be either an object or string");
        }

        JsonArray components = ctx.get("components").getAsJsonArray();
        for(int i = 0; i < components.size(); i++){
            JsonObject componentObj = components.get(i).getAsJsonObject();
            IPageComponent comp;
            switch(componentObj.get("type").getAsString().toLowerCase()){
                case "image": comp = new PageComponentImage(componentObj.get("ref").getAsString()); break;
                case "text": comp = new PageComponentText(componentObj.get("text").getAsString()); break;
                case "effect": {
                    IEffect effect = HerbariumApi.EFFECT_MANAGER.getEffect(componentObj.get("effect").getAsString());
                    comp = new PageComponentEffect(effect, componentObj.get("text").getAsString());
                    break;
                }
                default:{
                    throw new JsonParseException("Unknown component type: " + componentObj.get("type").getAsString());
                }
            }
            if(layout instanceof PageLayoutBorder){
                ((PageLayoutBorder) layout).add(comp, PageLayoutBorder.Position.valueOf(componentObj.get("position").getAsString().toUpperCase()));
            }
            renderer.add(comp);
        }

        return new PageBuilder()
                       .setGroup(group)
                       .setRenderer(renderer.layout(layout))
                       .setTitle(title)
                       .build();
    }

    public static final class JsonPageRenderer
    implements IPageRenderer{
        private final PageComponentContainer container;

        public JsonPageRenderer(PageComponentContainer container){
            this.container = container;
        }

        public JsonPageRenderer add(IPageComponent comp){
            this.container.register(comp);
            return this;
        }

        public JsonPageRenderer layout(IPageLayout layout){
            layout.layout(this.container);
            return this;
        }

        @Override
        public void render(float partialTicks) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(35.0F, 20.0F, 0.0F);
            for(IPageComponent comp : this.container){
                comp.render(0, 0, partialTicks);
            }
            GlStateManager.popMatrix();
        }
    }
}