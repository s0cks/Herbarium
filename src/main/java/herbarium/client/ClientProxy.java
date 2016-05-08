package herbarium.client;

import herbarium.client.render.RenderItemPageFP;
import herbarium.common.CommonProxy;
import herbarium.common.Herbarium;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
        registerRender(Herbarium.itemBrew, 0, "brew_remedy");
        registerRender(Herbarium.itemBrew, 1, "brew_spirit");
        registerRender(Herbarium.itemBrew, 2, "brew_venom");

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
        registerRender(Herbarium.blockLotus, "lotus");
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
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
            @Override
            public int getColorFromItemstack(ItemStack stack, int tintIndex) {
                return 0xFF0000;
            }
        }, Herbarium.itemBrew);
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