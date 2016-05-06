package herbarium.common;

import herbarium.api.HerbariumApi;
import herbarium.api.IFlower;
import herbarium.api.IFlowerManager;
import herbarium.api.brew.effects.IEffect;
import herbarium.api.brew.effects.IEffectManager;
import herbarium.api.commentarium.IPage;
import herbarium.api.commentarium.IPageManager;
import herbarium.api.genetics.IAllele;
import herbarium.api.genetics.IAlleleManager;
import herbarium.api.genetics.ISpecies;
import herbarium.api.ruins.IRuin;
import herbarium.api.ruins.IRuinManager;
import herbarium.client.gui.GuiJournal;
import herbarium.common.blocks.BlockBarrel;
import herbarium.common.blocks.BlockCaveFlower;
import herbarium.common.blocks.BlockWaterFlower;
import herbarium.common.blocks.BlockCoil;
import herbarium.common.blocks.BlockCrucible;
import herbarium.common.blocks.BlockFlume;
import herbarium.common.blocks.BlockHerbariumFlower;
import herbarium.common.blocks.BlockMortar;
import herbarium.common.blocks.BlockNetherFlower;
import herbarium.common.blocks.BlockPipe;
import herbarium.common.blocks.BlockJournal;
import herbarium.common.core.BiomeSpecificCaveGeneration;
import herbarium.common.core.BiomeSpecificGeneration;
import herbarium.common.core.Flowers;
import herbarium.common.core.Ruin;
import herbarium.common.core.RuinGenerator;
import herbarium.common.core.brew.effects.EffectTracker;
import herbarium.common.core.brew.effects.effect.EffectDebug;
import herbarium.common.core.commentarium.PageBuilder;
import herbarium.common.core.commentarium.PageTracker;
import herbarium.common.core.commentarium.renderer.MarkdownPageRenderer;
import herbarium.common.core.commentarium.renderer.TextPageRenderer;
import herbarium.common.items.ItemBrew;
import herbarium.common.items.ItemJournal;
import herbarium.common.items.ItemPage;
import herbarium.common.items.ItemPestle;
import herbarium.common.net.HerbariumNetwork;
import herbarium.common.tiles.TileEntityPipe;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
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

@Mod(
        modid = "herbarium",
        name = "Herbarium",
        version = "0.0.0.0",
        dependencies = "required-after:Forge@[1.8.9-11.15.1.1722,)"
)
public final class Herbarium
        implements IPageManager,
                   IEffectManager,
                   IGuiHandler,
                   IRuinManager,
                   IAlleleManager,
                   IFlowerManager {
    public static final CreativeTabs tab = new CreativeTabHerbarium();

    // Items
    public static final Item itemJournal = new ItemJournal()
                                                   .setCreativeTab(Herbarium.tab)
                                                   .setUnlocalizedName("herba_commentarium")
                                                   .setMaxStackSize(1);
    public static final Item itemPage = new ItemPage()
                                                .setCreativeTab(Herbarium.tab)
                                                .setUnlocalizedName("herba_page")
                                                .setMaxStackSize(1);
    public static final Item itemPestle = new ItemPestle()
                                                  .setCreativeTab(Herbarium.tab)
                                                  .setUnlocalizedName("herba_pestle")
                                                  .setMaxStackSize(1);
    public static final Item itemBrew = new ItemBrew()
                                                .setCreativeTab(Herbarium.tab)
                                                .setUnlocalizedName("herba_brew")
                                                .setMaxStackSize(1);
    // Blocks
    // Flowers
    public static final Block blockAlstromeria = new BlockHerbariumFlower(Flowers.ALSTROMERIA)
                                                         .setCreativeTab(Herbarium.tab)
                                                         .setUnlocalizedName("herba_alstromeria");
    public static final Block blockBelladonna = new BlockHerbariumFlower(Flowers.BELLADONNA)
                                                        .setCreativeTab(Herbarium.tab)
                                                        .setUnlocalizedName("herba_belladonna");
    public static final Block blockBlueAnemone = new BlockHerbariumFlower(Flowers.BLUE_ANEMONE)
                                                         .setCreativeTab(Herbarium.tab)
                                                         .setUnlocalizedName("herba_blue_anemone");
    public static final Block blockBlueberry = new BlockHerbariumFlower(Flowers.BLUEBERRY)
                                                       .setCreativeTab(Herbarium.tab)
                                                       .setUnlocalizedName("herba_blueberry");
    public static final Block blockButtercup = new BlockHerbariumFlower(Flowers.BUTTERCUP)
                                                       .setCreativeTab(Herbarium.tab)
                                                       .setUnlocalizedName("herba_buttercup");
    public static final Block blockCave = new BlockCaveFlower()
                                                  .setCreativeTab(Herbarium.tab)
                                                  .setUnlocalizedName("herba_cave");
    public static final Block blockDaisy = new BlockHerbariumFlower(Flowers.DAISY)
                                                   .setCreativeTab(Herbarium.tab)
                                                   .setUnlocalizedName("herba_daisy");
    public static final Block blockFire = new BlockNetherFlower(Flowers.FIRE)
                                                  .setCreativeTab(Herbarium.tab)
                                                  .setUnlocalizedName("herba_fire");
    public static final Block blockLongEarIris = new BlockHerbariumFlower(Flowers.LONG_EAR_IRIS)
                                                         .setCreativeTab(Herbarium.tab)
                                                         .setUnlocalizedName("herba_long_ear_iris");
    public static final Block blockLotus = new BlockWaterFlower()
                                                   .setCreativeTab(CreativeTabs.BREWING)
                                                   .setUnlocalizedName("herba_lotus");
    public static final Block blockNether = new BlockNetherFlower(Flowers.NETHER)
                                                    .setCreativeTab(Herbarium.tab)
                                                    .setUnlocalizedName("herba_nether");
    public static final Block blockTropicalBerries = new BlockHerbariumFlower(Flowers.TROPCIAL_BERRIES)
                                                             .setCreativeTab(Herbarium.tab)
                                                             .setUnlocalizedName("herba_tropical_berries");
    // Misc
    public static final Block blockCrucible = new BlockCrucible()
                                                      .setCreativeTab(Herbarium.tab)
                                                      .setUnlocalizedName("herba_crucible");
    public static final Block blockCoil = new BlockCoil()
                                                  .setCreativeTab(Herbarium.tab)
                                                  .setUnlocalizedName("herba_coil");
    public static final Block blockFlume = new BlockFlume()
                                                   .setCreativeTab(Herbarium.tab)
                                                   .setUnlocalizedName("herba_flume");
    public static final Block blockPipe = new BlockPipe()
                                                  .setCreativeTab(Herbarium.tab)
                                                  .setUnlocalizedName("herba_pipe");
    public static final Block blockMortar = new BlockMortar()
                                                    .setCreativeTab(Herbarium.tab)
                                                    .setUnlocalizedName("herba_mortar");
    public static final Block blockBarrel = new BlockBarrel()
                                                    .setCreativeTab(Herbarium.tab)
                                                    .setUnlocalizedName("herba_barrel");
    public static final Block blockJournal = new BlockJournal()
                                                    .setCreativeTab(CreativeTabs.BREWING)
                                                    .setUnlocalizedName("herba_journal");
    // GUIs
    public static final byte GUI_JOURNAL = 0x1;
    @Mod.Instance("herbarium")
    public static Herbarium instance;
    @SidedProxy(
            clientSide = "herbarium.client.ClientProxy",
            serverSide = "herbarium.common.CommonProxy"
    )
    public static CommonProxy proxy;
    private final Set<IPage> pages = new HashSet<>();
    private final List<IFlower> flowers = new LinkedList<>();
    private final List<IRuin> ruins = new LinkedList<>();
    private final List<IEffect> effects = new LinkedList<>();
    private final List<IAllele> alleles = new LinkedList<>();
    private final List<ISpecies> species = new LinkedList<>();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        HerbariumConfig.init(new Configuration(e.getSuggestedConfigurationFile()));
        System.setProperty("awt.useSystemAAFontSettings", "off");
        System.setProperty("swing.aatext", "false");

        HerbariumApi.PAGE_MANAGER = this;
        HerbariumApi.PAGE_TRACKER = new PageTracker();
        HerbariumApi.FLOWER_MANAGER = this;
        HerbariumApi.RUIN_MANAGER = this;
        HerbariumApi.EFFECT_MANAGER = this;
        HerbariumApi.EFFECT_TRACKER = new EffectTracker();

        this.register(new PageBuilder().setTitle("Commentarium")
                                       .setRenderer(new TextPageRenderer(new ResourceLocation("herbarium", "pages/Commentarium.txt")))
                                       .build());
        this.register(new PageBuilder().setTitle("Tropical Berries")
                                       .setRenderer(new MarkdownPageRenderer(new ResourceLocation("herbarium", "pages/TropicalBerries.md")))
                                       .build());
        this.register(new EffectDebug());

        String[] ruins = new String[]{
                "basic",
                "wooden"
        };

        for (String str : ruins) {
            register(new Ruin(str));
        }

        // Items
        GameRegistry.registerItem(itemJournal, "journal");
        GameRegistry.registerItem(itemPage, "page");
        GameRegistry.registerItem(itemPestle, "pestle");
        GameRegistry.registerItem(itemBrew, "brew");

        // Blocks
        // Flowers
        GameRegistry.registerBlock(blockAlstromeria, "alstromeria");
        GameRegistry.registerBlock(blockBelladonna, "belladonna");
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
        GameRegistry.registerBlock(blockMortar, "mortar");
        GameRegistry.registerBlock(blockBarrel, "barrel");
        GameRegistry.registerBlock(blockJournal, "journal_block");

        // Tiles
        GameRegistry.registerTileEntity(TileEntityPipe.class, "pipe");

        proxy.registerRenders();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, instance);

        proxy.registerColors();

        Flowers.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        HerbariumNetwork.init();

        MinecraftForge.EVENT_BUS.register(HerbariumApi.PAGE_TRACKER);
        MinecraftForge.EVENT_BUS.register(HerbariumApi.EFFECT_TRACKER);
        MinecraftForge.EVENT_BUS.register(new RuinGenerator());

        MinecraftForge.EVENT_BUS.register(new BiomeSpecificGeneration(Biomes.FOREST, blockAlstromeria));
        MinecraftForge.EVENT_BUS.register(new BiomeSpecificGeneration(Biomes.BIRCH_FOREST, blockButtercup));
        MinecraftForge.EVENT_BUS.register(new BiomeSpecificGeneration(Biomes.EXTREME_HILLS, blockLongEarIris));
        MinecraftForge.EVENT_BUS.register(new BiomeSpecificGeneration(Biomes.JUNGLE, blockTropicalBerries));
        MinecraftForge.EVENT_BUS.register(new BiomeSpecificCaveGeneration(Biomes.EXTREME_HILLS, blockCave, 30));
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent e) {
        e.registerServerCommand(new CommandBase() {
            @Override
            public String getCommandName() {
                return "pages_all";
            }

            @Override
            public String getCommandUsage(ICommandSender sender) {
                return "pages_all";
            }

            @Override
            public void execute(MinecraftServer server, ICommandSender sender, String[] args)
            throws CommandException {
                for (IPage page : HerbariumApi.PAGE_MANAGER.all()) {
                    if (!HerbariumApi.PAGE_TRACKER.learned(((EntityPlayer) sender), page)) {
                        HerbariumApi.PAGE_TRACKER.learn(((EntityPlayer) sender), page);
                    }
                }
                HerbariumApi.PAGE_TRACKER.sync(((EntityPlayer) sender));
            }
        });
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
    public void register(IPage page) {
        this.pages.add(page);
    }

    @Override
    public IFlower getFlower(String uuid) {
        for (IFlower f : this.flowers) {
            if (f.uuid()
                 .equals(uuid)) {
                return f;
            }
        }

        return null;
    }

    @Override
    public void register(IFlower flower) {
        for (IFlower f : this.flowers) {
            if (f.uuid()
                 .equals(flower.uuid())) {
                return;
            }
        }

        this.flowers.add(flower);
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
    public void register(IRuin ruin) {
        for (IRuin r : this.ruins) {
            if (r.uuid()
                 .equals(ruin.uuid())) {
                return;
            }
        }

        this.ruins.add(ruin);
    }

    @Override
    public IRuin getRuin(String uuid) {
        for (IRuin r : this.ruins) {
            if (r.uuid()
                 .equals(uuid)) {
                return r;
            }
        }

        return null;
    }

    @Override
    public IRuin getRandom(Random rand) {
        for (IRuin ruin : this.ruins) {
            if (rand.nextBoolean()) {
                return ruin;
            }
        }

        return this.ruins.get(0);
    }

    @Override
    public void register(IEffect effect) {
        for (IEffect e : this.effects) {
            if (e.uuid()
                 .equals(effect.uuid())) {
                return;
            }
        }

        this.effects.add(effect);
    }

    @Override
    public IEffect getEffect(String uuid) {
        for (IEffect e : this.effects) {
            if (e.uuid()
                 .equals(uuid)) {
                return e;
            }
        }

        return null;
    }

    @Override
    public void registerSpecies(ISpecies species) {
        for (ISpecies s : this.species) {
            if (s.uuid()
                 .equals(species.uuid())) {
                return;
            }
        }

        this.species.add(species);
    }

    @Override
    public void registerAllele(IAllele allele) {
        for (IAllele a : this.alleles) {
            if (a.uuid()
                 .equals(allele.uuid())) {
                return;
            }
        }

        this.alleles.add(allele);
    }

    @Override
    public ISpecies getSpecies(String uuid) {
        for (ISpecies s : this.species) {
            if (s.uuid()
                 .equals(uuid)) {
                return s;
            }
        }

        return null;
    }

    @Override
    public IAllele getAllele(String uuid) {
        for (IAllele a : this.alleles) {
            if (a.uuid()
                 .equals(uuid)) {
                return a;
            }
        }

        return null;
    }
}