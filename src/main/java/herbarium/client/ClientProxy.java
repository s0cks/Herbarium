package herbarium.client;

import herbarium.client.render.RenderEffectTray;
import herbarium.client.render.RenderItemPageFP;
import herbarium.client.render.tile.RenderTileMortar;
import herbarium.common.CommonProxy;
import herbarium.common.HerbariumBlocks;
import herbarium.common.HerbariumItems;
import herbarium.common.blocks.flowers.BlockWaterFlower;
import herbarium.common.items.ItemBrew;
import herbarium.common.items.ItemPaste;
import herbarium.common.tiles.brewing.TileEntityMortar;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public final class ClientProxy
extends CommonProxy {
  private RenderEffectTray renderEffectTray;

  @Override
  public ModelManager modelManager() {
    return this.dispatcher()
               .getBlockModelShapes()
               .getModelManager();
  }

  @Override
  public BlockRendererDispatcher dispatcher() {
    return this.getClient()
               .getBlockRendererDispatcher();
  }

  @Override
  public void registerRenders() {
    // =========================================================================
    // Items
    registerRender(HerbariumItems.itemJournal, "journal");
    registerRender(HerbariumItems.itemPage, "page");
    registerRender(HerbariumItems.itemPestle, "pestle");
    registerRender(HerbariumItems.itemBrew, 0, "remedy");
    registerRender(HerbariumItems.itemBrew, 1, "spirit");
    registerRender(HerbariumItems.itemBrew, 2, "venom");
    for (int i = 0; i < ItemPaste.names.length; i++) {
      registerRender(HerbariumItems.itemPaste, i, ItemPaste.names[i] + "_paste");
    }
    // =========================================================================

    // =========================================================================
    // Blocks
    registerRender(HerbariumBlocks.blockAlstromeria, "alstromeria");
    registerRender(HerbariumBlocks.blockBelladonna, "belladonna");
    registerRender(HerbariumBlocks.blockBlueAnemone, "anemone");
    registerRender(HerbariumBlocks.blockBlueberryBlossom, "blueberry_blossom");
    registerRender(HerbariumBlocks.blockButtercup, "buttercup");
    registerRender(HerbariumBlocks.blockCavernBloom, "cavern_bloom");
    registerRender(HerbariumBlocks.blockWinterLily, "winter_lily");
    registerRender(HerbariumBlocks.blockLancetRoot, "lancet_root");
    registerRender(HerbariumBlocks.blockTailIris, "tail_iris");
    registerRender(HerbariumBlocks.blockSpringLotus, "spring_lotus");
    registerRender(HerbariumBlocks.blockIgneousSpear, "igneous_spear");
    registerRender(HerbariumBlocks.blockTropicalBerries, "tropical_berries");

    registerRender(HerbariumBlocks.blockCoil, "coil");
    registerRender(HerbariumBlocks.blockCrucible, "crucible");
    registerRender(HerbariumBlocks.blockFlume, "flume");
    registerRender(HerbariumBlocks.blockPipe, "pipe");
    registerRender(HerbariumBlocks.blockMortar, "mortar");
    registerRender(HerbariumBlocks.blockBarrel, "barrel");
    registerRender(HerbariumBlocks.blockJournal, "journal_block");
    // =========================================================================

    // =========================================================================
    // Misc
    MinecraftForge.EVENT_BUS.register(new RenderItemPageFP());
    MinecraftForge.EVENT_BUS.register(new ClientEffectHandler());
    // =========================================================================
  }

  @Override
  public void registerColors() {
    // =========================================================================
    // Items
    registerColor(new ItemBrew.BrewColorizer(), HerbariumItems.itemBrew);
    // =========================================================================

    // =========================================================================
    // Blocks
    registerColor(new BlockWaterFlower.SpringLotusColorizer(), HerbariumBlocks.blockSpringLotus);
    // =========================================================================
  }

  @Override
  public Minecraft getClient() {
    return FMLClientHandler.instance()
                           .getClient();
  }

  @Override
  public RenderEffectTray renderEffectTray() {
    return this.renderEffectTray;
  }

  @Override
  public void init() {
    // MinecraftForge.EVENT_BUS.register((this.renderEffectTray = new RenderEffectTray()));

    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMortar.class, new RenderTileMortar());
  }

  private void registerColor(IItemColor color, Item item) {
    this.getClient()
        .getItemColors()
        .registerItemColorHandler(color, item);
  }

  private void registerColor(IBlockColor color, Block block) {
    this.getClient()
        .getBlockColors()
        .registerBlockColorHandler(color, block);
  }

  private void registerRender(Block block, String id) {
    ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(new ResourceLocation("herbarium", id), "inventory"));
  }

  private void registerRender(Item item, int meta, String id) {
    ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation("herbarium:" + id, "inventory"));
  }

  private void registerRender(Item item, String id) {
    ModelLoader.setCustomModelResourceLocation(
    item,
    0,
    new ModelResourceLocation("herbarium:" + id, "inventory"));
  }
}