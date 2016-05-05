package herbarium.client;

import herbarium.common.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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
        /*
        // Items
        registerRender(Herbarium.itemJournal, "journal");
        registerRender(Herbarium.itemPage, "page");
        registerRender(Herbarium.itemDust, "dust");
        registerRender(Herbarium.itemMortarAndPestle, "mortar_pestle");

        // Blocks
        registerRender(Herbarium.blockAlstromeria, "alstromeria");
        registerRender(Herbarium.blockBelladonna, "belladonna");
        registerRender(Herbarium.blockBlueAnemone, "blue_anemone");
        registerRender(Herbarium.blockBlueberry, "blueberry");
        registerRender(Herbarium.blockButtercup, "buttercup");
        registerRender(Herbarium.blockCave, "cave");
        registerRender(Herbarium.blockDaisy, "daisy");
        registerRender(Herbarium.blockFire, "fire");
        registerRender(Herbarium.blockLongEarIris, "long_ear_iris");
        registerRender(Herbarium.blockLotus, "lotus");
        registerRender(Herbarium.blockNether, "nether");
        registerRender(Herbarium.blockTropicalBerries, "tropical_berries");
        registerRender(Herbarium.blockCoil, "coil");
        registerRender(Herbarium.blockCrucible, "crucible");
        registerRender(Herbarium.blockFlume, "flume");
        registerRender(Herbarium.blockPipe, "pipe");
        */
    }

    private void registerRender(Block block, String id){
        registerRender(Item.getItemFromBlock(block), id);
    }

    private void registerRender(Item item, int meta, String id){
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation("herbarium:" + id, "inventory"));
    }

    private void registerRender(Item item, String id){
    	ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation("herbarium:" + id, "inventory"));
    }
}