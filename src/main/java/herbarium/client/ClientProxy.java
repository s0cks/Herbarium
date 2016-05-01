package herbarium.client;

import herbarium.common.CommonProxy;
import herbarium.common.Herbarium;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.FMLClientHandler;

public final class ClientProxy
extends CommonProxy{
    @Override
    public Minecraft getClient(){
        return FMLClientHandler.instance().getClient();
    }

    @Override
    public void registerRenders(){
        registerRender(Herbarium.itemJournal, "journal");
    }

    private void registerRender(Item item, String id){
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation("herbarium:" + id, "inventory"));
    }
}