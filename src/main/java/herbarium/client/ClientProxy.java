package herbarium.client;

import herbarium.client.render.RenderItemPageFP;
import herbarium.common.CommonProxy;
import herbarium.common.Herbarium;
import herbarium.common.blocks.BlockWaterFlower;
import herbarium.common.items.ItemBrew;
import herbarium.common.items.ItemPaste;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;

public final class ClientProxy
extends CommonProxy{
    @Override
    public Minecraft getClient(){
        return FMLClientHandler.instance().getClient();
    }

    @Override
    public void registerRenders(){
        // Items
        registerRender(Herbarium.itemJournal, "journal");
        registerRender(Herbarium.itemPage, "page");
        registerRender(Herbarium.itemPestle, "pestle");
        registerRender(Herbarium.itemBrew, 0, "remedy");
        registerRender(Herbarium.itemBrew, 1, "spirit");
        registerRender(Herbarium.itemBrew, 2, "venom");

        for(int i = 0; i < ItemPaste.names.length; i++){
            registerRender(Herbarium.itemPaste, i, ItemPaste.names[i] + "_paste");
        }

        // Blocks
        registerRender(Herbarium.blockAlstromeria, "alstromeria");
        registerRender(Herbarium.blockBelladonna, "belladonna");
        registerRender(Herbarium.blockBlueAnemone, "anemone");
        registerRender(Herbarium.blockBlueberry, "blueberry_blossom");
        registerRender(Herbarium.blockButtercup, "buttercup");
        registerRender(Herbarium.blockCave, "cavern_bloom");
        registerRender(Herbarium.blockWinterLily, "winter_lily");
        registerRender(Herbarium.blockFire, "lancet_root");
        registerRender(Herbarium.blockLongEarIris, "tail_iris");
        registerRender(Herbarium.blockLotus, "spring_lotus");
        registerRender(Herbarium.blockNether, "igneous_spear");
        registerRender(Herbarium.blockTropicalBerries, "tropical_berries");
        registerRender(Herbarium.blockCoil, "coil");
        registerRender(Herbarium.blockCrucible, "crucible");
        registerRender(Herbarium.blockFlume, "flume");
        registerRender(Herbarium.blockPipe, "pipe");
        registerRender(Herbarium.blockMortar, "mortar");
        registerRender(Herbarium.blockBarrel, "barrel");
        registerRender(Herbarium.blockJournal, "journal_block");

        MinecraftForge.EVENT_BUS.register(new RenderItemPageFP());
        MinecraftForge.EVENT_BUS.register(new ClientEffectHandler());
    }

    @Override
    public void registerColors(){
        registerColor(new ItemBrew.BrewColorizer(), Herbarium.itemBrew);
        registerColor(new BlockWaterFlower.SpringLotusColorizer(), Herbarium.blockLotus);
    }

    private void registerColor(IItemColor color, Item item){
        this.getClient().getItemColors().registerItemColorHandler(color, item);
    }

    private void registerColor(IBlockColor color, Block block){
        this.getClient().getBlockColors().registerBlockColorHandler(color, block);
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