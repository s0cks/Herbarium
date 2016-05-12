package herbarium.common.core.commentarium;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import herbarium.api.commentarium.IPage;
import herbarium.api.commentarium.IPageGroup;
import herbarium.api.commentarium.IPageRenderer;
import herbarium.client.page.IPageComponent;
import herbarium.client.page.IPageComponentLayout;
import herbarium.client.page.PageComponentContainer;
import herbarium.client.page.components.PageComponentImage;
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
        IPageComponentLayout layout;
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
            switch(componentObj.get("type").getAsString().toLowerCase()){
                case "image": renderer.add(new PageComponentImage(componentObj.get("ref").getAsString())); break;
            }
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

        public JsonPageRenderer layout(IPageComponentLayout layout){
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