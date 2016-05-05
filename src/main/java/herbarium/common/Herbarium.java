package herbarium.common;

import herbarium.api.HerbariumApi;
import herbarium.api.IFlower;
import herbarium.api.IFlowerManager;
import herbarium.api.brew.IMixer;
import herbarium.api.brew.IMixerFactory;
import herbarium.api.commentarium.IPage;
import herbarium.api.commentarium.IPageManager;
import herbarium.api.ruins.IRuin;
import herbarium.api.ruins.IRuinManager;
import herbarium.client.gui.GuiJournal;
import herbarium.common.blocks.BlockCaveFlower;
import herbarium.common.blocks.BlockCoil;
import herbarium.common.blocks.BlockCrucible;
import herbarium.common.blocks.BlockFlume;
import herbarium.common.blocks.BlockHerbariumFlower;
import herbarium.common.blocks.BlockNetherFlower;
import herbarium.common.blocks.BlockPipe;
import herbarium.common.core.BiomeSpecificCaveGeneration;
import herbarium.common.core.BiomeSpecificGeneration;
import herbarium.common.core.Flowers;
import herbarium.common.core.Ruin;
import herbarium.common.core.RuinGenerator;
import herbarium.common.core.brew.BrewLevelManager;
import herbarium.common.core.brew.Mixer;
import herbarium.common.core.commentarium.PageBuilder;
import herbarium.common.core.commentarium.PageTracker;
import herbarium.common.items.ItemJournal;
import herbarium.common.items.ItemPage;
import herbarium.common.net.HerbariumNetwork;
import herbarium.common.tiles.TileEntityPipe;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Mod(modid = "herbarium",
     name = "Herbarium",
     version = "0.0.0.0",
     dependencies = "required-after:Forge@[1.8.9-11.15.1.1722,)")
public final class Herbarium
        implements IPageManager,
                   IGuiHandler,
                   IMixerFactory,
                   IRuinManager,
                   IFlowerManager{
    @Mod.Instance("herbarium")
    public static Herbarium instance;

    @SidedProxy(clientSide = "herbarium.client.ClientProxy",
                serverSide = "herbarium.common.CommonProxy")
    public static CommonProxy proxy;

    // Items
    public static final Item itemJournal = new ItemJournal()
                                                   .setCreativeTab(CreativeTabs.BREWING)
                                                   .setUnlocalizedName("herba_commentarium")
                                                   .setMaxStackSize(1);
    public static final Item itemPage = new ItemPage()
                                                .setCreativeTab(CreativeTabs.BREWING)
                                                .setUnlocalizedName("herba_page")
                                                .setMaxStackSize(1);

    // Blocks
    // Flowers
    public static final Block blockAlstromeria = new BlockHerbariumFlower(Flowers.ALSTROMERIA)
                                                         .setCreativeTab(CreativeTabs.BREWING)
                                                         .setUnlocalizedName("herba_alstromeria");
    public static final Block blockBelladonna = new BlockHerbariumFlower(Flowers.BELLADONNA)
                                                        .setCreativeTab(CreativeTabs.BREWING)
                                                        .setUnlocalizedName("herba_belladonna");
    public static final Block blockBlueAnemone = new BlockHerbariumFlower(Flowers.BLUE_ANEMONE)
                                                         .setCreativeTab(CreativeTabs.BREWING)
                                                         .setUnlocalizedName("herba_blue_anemone");
    public static final Block blockBlueberry = new BlockHerbariumFlower(Flowers.BLUEBERRY)
                                                       .setCreativeTab(CreativeTabs.BREWING)
                                                       .setUnlocalizedName("herba_blueberry");
    public static final Block blockButtercup = new BlockHerbariumFlower(Flowers.BUTTERCUP)
                                                       .setCreativeTab(CreativeTabs.BREWING)
                                                       .setUnlocalizedName("herba_buttercup");
    public static final Block blockCave = new BlockCaveFlower()
                                                  .setCreativeTab(CreativeTabs.BREWING)
                                                  .setUnlocalizedName("herba_cave");
    public static final Block blockDaisy = new BlockHerbariumFlower(Flowers.DAISY)
                                                   .setCreativeTab(CreativeTabs.BREWING)
                                                   .setUnlocalizedName("herba_daisy");
    public static final Block blockFire = new BlockNetherFlower(Flowers.FIRE)
                                                  .setCreativeTab(CreativeTabs.BREWING)
                                                  .setUnlocalizedName("herba_fire");
    public static final Block blockLongEarIris = new BlockHerbariumFlower(Flowers.LONG_EAR_IRIS)
                                                         .setCreativeTab(CreativeTabs.BREWING)
                                                         .setUnlocalizedName("herba_long_ear_iris");
    public static final Block blockLotus = new BlockHerbariumFlower(Flowers.LOTUS)
                                                   .setCreativeTab(CreativeTabs.BREWING)
                                                   .setUnlocalizedName("herba_lotus");
    public static final Block blockNether = new BlockNetherFlower(Flowers.NETHER)
                                                    .setCreativeTab(CreativeTabs.BREWING)
                                                    .setUnlocalizedName("herba_nether");
    public static final Block blockTropicalBerries = new BlockHerbariumFlower(Flowers.TROPCIAL_BERRIES)
                                                             .setCreativeTab(CreativeTabs.BREWING)
                                                             .setUnlocalizedName("herba_tropical_berries");

    // Misc
    public static final Block blockCrucible = new BlockCrucible()
            .setCreativeTab(CreativeTabs.BREWING)
            .setUnlocalizedName("herba_crucible");
    public static final Block blockCoil = new BlockCoil()
            .setCreativeTab(CreativeTabs.BREWING)
            .setUnlocalizedName("herba_coil");
    public static final Block blockFlume = new BlockFlume()
            .setCreativeTab(CreativeTabs.BREWING)
            .setUnlocalizedName("herba_flume");
    public static final Block blockPipe = new BlockPipe()
            .setCreativeTab(CreativeTabs.BREWING)
            .setUnlocalizedName("herba_pipe");

    // GUIs
    public static final byte GUI_JOURNAL = 0x1;

    private final Set<IPage> pages = new HashSet<>();
    private final List<IFlower> flowers = new LinkedList<>();
    private final List<IRuin> ruins = new LinkedList<>();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        HerbariumConfig.init(new Configuration(e.getSuggestedConfigurationFile()));

        HerbariumApi.PAGE_MANAGER = this;
        HerbariumApi.PAGE_TRACKER = new PageTracker();
        HerbariumApi.MIXER_FACTORY = this;
        HerbariumApi.FLOWER_MANAGER = this;
        HerbariumApi.RUIN_MANAGER = this;

        String[] titles = new String[]{
            "Commentarium",
            "Alstromeria",
            "Belladonna",
            "Blue Anemone",
            "Blueberry",
            "Buttercup",
            "Cave",
            "Daisy",
            "Fire",
            "Long Ear Iris",
            "Lotus",
            "Nether",
            "Tropical Berries"
        };

        for(String str : titles){
            register(new PageBuilder().setTitle(str).build());
        }

        String[] ruins = new String[]{
            "basic",
            "wooden"
        };

        for(String str : ruins){
            register(new Ruin(str));
        }

        // Items
        GameRegistry.registerItem(itemJournal, "journal");
        GameRegistry.registerItem(itemPage, "page");

        // Blocks
        // Flowers
        GameRegistry.registerBlock(blockAlstromeria, "alstromeria");
        GameRegistry.registerBlock(blockBelladonna, "belledonna");
        GameRegistry.registerBlock(blockBlueAnemone, "blue_anemone");
        GameRegistry.registerBlock(blockBlueberry, "blueberry");
        GameRegistry.registerBlock(blockButtercup, "buttercup");
        GameRegistry.registerBlock(blockCave, "cave");
        GameRegistry.registerBlock(blockDaisy, "daisy");
        GameRegistry.registerBlock(blockFire, "fire");
        GameRegistry.registerBlock(blockLongEarIris, "long_ear_iris");
        GameRegistry.registerBlock(blockLotus, "lotus");
        GameRegistry.registerBlock(blockNether, "nether");
        GameRegistry.registerBlock(blockTropicalBerries, "tropical_berries");

        // Misc
        GameRegistry.registerBlock(blockCrucible, "crucible");
        GameRegistry.registerBlock(blockCoil, "coil");
        GameRegistry.registerBlock(blockFlume, "flume");
        GameRegistry.registerBlock(blockPipe, "pipe");

        // Tiles
        GameRegistry.registerTileEntity(TileEntityPipe.class, "pipe");

        proxy.registerRenders();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, instance);

        Flowers.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        HerbariumNetwork.init();

        MinecraftForge.EVENT_BUS.register(BrewLevelManager.INSTANCE);
        MinecraftForge.EVENT_BUS.register(HerbariumApi.PAGE_TRACKER);
        MinecraftForge.EVENT_BUS.register(new RuinGenerator());

        MinecraftForge.EVENT_BUS.register(new BiomeSpecificGeneration(Biomes.FOREST, blockAlstromeria));
        MinecraftForge.EVENT_BUS.register(new BiomeSpecificGeneration(Biomes.BIRCH_FOREST, blockButtercup));
        MinecraftForge.EVENT_BUS.register(new BiomeSpecificGeneration(Biomes.EXTREME_HILLS, blockLongEarIris));
        MinecraftForge.EVENT_BUS.register(new BiomeSpecificGeneration(Biomes.JUNGLE, blockTropicalBerries));
        MinecraftForge.EVENT_BUS.register(new BiomeSpecificCaveGeneration(Biomes.EXTREME_HILLS, blockCave, 30));
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent e) {
    }

    @Override
    public Set<IPage> all() {
        return Collections.unmodifiableSet(this.pages);
    }

    @Override
    public IPage get(String uuid) {
        for (IPage page : this.pages) {
            if (page.uuid()
                    .equals(uuid)) {
                return page;
            }
        }

        return null;
    }

    @Override
    public IFlower getFlower(String uuid) {
        for(IFlower f : this.flowers){
            if(f.uuid().equals(uuid)){
                return f;
            }
        }

        return null;
    }

    @Override
    public void register(IFlower flower) {
        for(IFlower f : this.flowers){
            if(f.uuid().equals(flower.uuid())){
                return;
            }
        }

        this.flowers.add(flower);
    }

    @Override
    public void register(IPage page) {
        this.pages.add(page);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case GUI_JOURNAL:
                return new GuiJournal(player);
            default:
                return null;
        }
    }

    @Override
    public IMixer newMixer() {
        return new Mixer();
    }

    @Override
    public void register(IRuin ruin) {
        for(IRuin r : this.ruins){
            if(r.uuid().equals(ruin.uuid())){
                return;
            }
        }

        this.ruins.add(ruin);
    }

    @Override
    public IRuin getRuin(String uuid) {
        for(IRuin r : this.ruins){
            if(r.uuid().equals(uuid)){
                return r;
            }
        }

        return null;
    }

    @Override
    public IRuin getRandom(Random rand) {
        for(IRuin ruin : this.ruins){
            if(rand.nextBoolean()){
                return ruin;
            }
        }

        return this.ruins.get(0);
    }
}