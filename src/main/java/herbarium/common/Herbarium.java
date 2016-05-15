package herbarium.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import herbarium.api.HerbariumApi;
import herbarium.api.IGemOreTracker;
import herbarium.api.brew.effects.IEffect;
import herbarium.api.brew.effects.IEffectManager;
import herbarium.api.commentarium.pages.IPage;
import herbarium.api.commentarium.pages.IPageGroup;
import herbarium.api.commentarium.pages.IPageManager;
import herbarium.api.genetics.IAllele;
import herbarium.api.genetics.IAlleleManager;
import herbarium.api.genetics.IChromosomeType;
import herbarium.api.genetics.ISpecies;
import herbarium.api.ruins.IRuin;
import herbarium.api.ruins.IRuinManager;
import herbarium.client.font.HerbariumFontRenderer;
import herbarium.client.gui.GuiJournal;
import herbarium.common.blocks.BlockBarrel;
import herbarium.common.blocks.BlockCaveFlower;
import herbarium.common.blocks.BlockCoil;
import herbarium.common.blocks.BlockCrucible;
import herbarium.common.blocks.BlockDebug;
import herbarium.common.blocks.BlockFlume;
import herbarium.common.blocks.BlockHerbariumFlower;
import herbarium.common.blocks.BlockJournal;
import herbarium.common.blocks.BlockMortar;
import herbarium.common.blocks.BlockNetherFlower;
import herbarium.common.blocks.BlockPipe;
import herbarium.common.blocks.BlockWaterFlower;
import herbarium.common.core.BiomeSpecificCaveGeneration;
import herbarium.common.core.BiomeSpecificGeneration;
import herbarium.common.core.KeyHandler;
import herbarium.common.core.Ruin;
import herbarium.common.core.RuinGenerator;
import herbarium.common.core.botany.FlowerFactory;
import herbarium.common.core.botany.FlowerSpecies;
import herbarium.common.core.botany.Flowers;
import herbarium.common.core.brew.effects.EffectTracker;
import herbarium.common.core.brew.effects.effect.RemedyEffects;
import herbarium.common.core.brew.effects.effect.SpiritEffects;
import herbarium.common.core.brew.effects.effect.VenomEffects;
import herbarium.common.core.commentarium.JsonPageDeserializer;
import herbarium.common.core.commentarium.PageGroups;
import herbarium.common.core.commentarium.PageTracker;
import herbarium.common.core.genetics.AlleleManager;
import herbarium.common.core.journal.EnumJournalChapters;
import herbarium.common.core.journal.JournalFactory;
import herbarium.common.items.ItemBrew;
import herbarium.common.items.ItemJournal;
import herbarium.common.items.ItemPage;
import herbarium.common.items.ItemPaste;
import herbarium.common.items.ItemPestle;
import herbarium.common.net.HerbariumNetwork;
import herbarium.common.tiles.TileEntityBrewBarrel;
import herbarium.common.tiles.TileEntityMortar;
import herbarium.common.tiles.TileEntityPipe;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Mod(
modid = "herbarium",
name = "Herbarium",
version = "0.0.0.0",
dependencies = "required-after:Forge@[1.9-12.16.1.1887,)"
)
public final class Herbarium
implements IPageManager,
           IEffectManager,
           IGuiHandler,
           IRuinManager,
           IAlleleManager,
           IGemOreTracker {
  public static final Random random = new Random();
  public static final Gson gson = new GsonBuilder()
                                  .registerTypeAdapter(IPage.class, new JsonPageDeserializer())
                                  .create();
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
  public static final Item itemPaste = new ItemPaste()
                                       .setCreativeTab(Herbarium.tab)
                                       .setUnlocalizedName("herba_paste");
  // Blocks
  // Flowers
  public static final Block blockAlstromeria = new BlockHerbariumFlower(Flowers.ALSTROMERIA.individual())
                                               .setCreativeTab(Herbarium.tab)
                                               .setUnlocalizedName("herba_alstromeria");
  public static final Block blockBelladonna = new BlockHerbariumFlower(Flowers.BELLADONNA.individual())
                                              .setCreativeTab(Herbarium.tab)
                                              .setUnlocalizedName("herba_belladonna");
  public static final Block blockBlueAnemone = new BlockHerbariumFlower(Flowers.BLUE_ANEMONE.individual())
                                               .setCreativeTab(Herbarium.tab)
                                               .setUnlocalizedName("herba_anemone");
  public static final Block blockBlueberry = new BlockHerbariumFlower(Flowers.BLUEBERRY_BLOSSOM.individual())
                                             .setCreativeTab(Herbarium.tab)
                                             .setUnlocalizedName("herba_blueberry_blossom");
  public static final Block blockButtercup = new BlockHerbariumFlower(Flowers.BUTTERCUP.individual())
                                             .setCreativeTab(Herbarium.tab)
                                             .setUnlocalizedName("herba_buttercup");
  public static final Block blockCave = new BlockCaveFlower(Flowers.CAVERN_BLOOM.individual())
                                        .setCreativeTab(Herbarium.tab)
                                        .setUnlocalizedName("herba_cavern_bloom");
  public static final Block blockWinterLily = new BlockHerbariumFlower(Flowers.WINTER_LILY.individual())
                                              .setCreativeTab(Herbarium.tab)
                                              .setUnlocalizedName("herba_winter_lily");
  public static final Block blockFire = new BlockNetherFlower(Flowers.LANCET_ROOT.individual())
                                        .setCreativeTab(Herbarium.tab)
                                        .setUnlocalizedName("herba_lancet_root");
  public static final Block blockLongEarIris = new BlockHerbariumFlower(Flowers.TAIL_IRIS.individual())
                                               .setCreativeTab(Herbarium.tab)
                                               .setUnlocalizedName("herba_tail_iris");
  public static final Block blockLotus = new BlockWaterFlower(Flowers.SPRING_LOTUS.individual())
                                         .setCreativeTab(Herbarium.tab)
                                         .setUnlocalizedName("herba_spring_lotus");
  public static final Block blockNether = new BlockNetherFlower(Flowers.IGNEOUS_SPEAR.individual())
                                          .setCreativeTab(Herbarium.tab)
                                          .setUnlocalizedName("herba_igneous_spear");
  public static final Block blockTropicalBerries = new BlockHerbariumFlower(Flowers.TROPICAL_BERRIES.individual())
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
                                           .setCreativeTab(Herbarium.tab)
                                           .setUnlocalizedName("herba_journal");
  public static final Block blockDebug = new BlockDebug()
                                         .setCreativeTab(Herbarium.tab)
                                         .setUnlocalizedName("herba_debug");
  // GUIs
  public static final byte GUI_JOURNAL = 0x1;
  @Mod.Instance("herbarium")
  public static Herbarium instance;
  @SidedProxy(
  clientSide = "herbarium.client.ClientProxy",
  serverSide = "herbarium.common.CommonProxy"
  )
  public static CommonProxy proxy;

  static {
    HerbariumApi.FLOWER_FACTORY = new FlowerFactory();
    HerbariumApi.ALLELE_MANAGER = new AlleleManager();
    HerbariumApi.FLOWER_SPECIES = new FlowerSpecies();

    Flowers.initFlowers();
  }

  private final Set<IPage> pages = new HashSet<>();
  private final List<IRuin> ruins = new LinkedList<>();
  private final List<IEffect> effects = new LinkedList<>();
  private final List<IAllele> alleles = new LinkedList<>();
  private final List<ISpecies> species = new LinkedList<>();
  private final List<IPageGroup> groups = new LinkedList<>();
  private final List<Block> gems = new LinkedList<>();

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent e) {
    HerbariumConfig.init(new Configuration(e.getSuggestedConfigurationFile()));

    HerbariumApi.PAGE_MANAGER = this;
    HerbariumApi.PAGE_TRACKER = new PageTracker();
    HerbariumApi.RUIN_MANAGER = this;
    HerbariumApi.EFFECT_MANAGER = this;
    HerbariumApi.EFFECT_TRACKER = new EffectTracker();
    HerbariumApi.GEM_TRACKER = this;
    HerbariumApi.FONT_RENDERER = new HerbariumFontRenderer();
    HerbariumApi.JOURNAL_FACTORY = new JournalFactory();

    for (PageGroups group : PageGroups.values()) this.register(group);
    for (VenomEffects effect : VenomEffects.values()) this.register(effect);
    for (SpiritEffects effect : SpiritEffects.values()) this.register(effect);
    for (RemedyEffects effect : RemedyEffects.values()) this.register(effect);

    register(Blocks.DIAMOND_ORE);
    register(Blocks.EMERALD_ORE);
    register(Blocks.QUARTZ_ORE);
    register(Blocks.LAPIS_ORE);
    register(Blocks.REDSTONE_ORE);
    register(Blocks.LIT_REDSTONE_ORE);

    // Items
    GameRegistry.registerItem(itemJournal, "journal");
    GameRegistry.registerItem(itemPage, "page");
    GameRegistry.registerItem(itemPestle, "pestle");
    GameRegistry.registerItem(itemBrew, "brew");
    GameRegistry.registerItem(itemPaste, "paste");

    // Blocks
    // Flowers
    GameRegistry.registerBlock(blockAlstromeria, "alstromeria");
    GameRegistry.registerBlock(blockBelladonna, "belladonna");
    GameRegistry.registerBlock(blockBlueAnemone, "anemone");
    GameRegistry.registerBlock(blockBlueberry, "blueberry_blossom");
    GameRegistry.registerBlock(blockButtercup, "buttercup");
    GameRegistry.registerBlock(blockCave, "cavern_bloom");
    GameRegistry.registerBlock(blockWinterLily, "winter_lily");
    GameRegistry.registerBlock(blockFire, "lancet_root");
    GameRegistry.registerBlock(blockLongEarIris, "tail_iris");
    GameRegistry.registerBlock(blockLotus, "spring_lotus");
    GameRegistry.registerBlock(blockNether, "igneous_spear");
    GameRegistry.registerBlock(blockTropicalBerries, "tropical_berries");

    // Misc
    GameRegistry.registerBlock(blockCrucible, "crucible");
    GameRegistry.registerBlock(blockCoil, "coil");
    GameRegistry.registerBlock(blockFlume, "flume");
    GameRegistry.registerBlock(blockPipe, "pipe");
    GameRegistry.registerBlock(blockMortar, "mortar");
    GameRegistry.registerBlock(blockBarrel, "barrel");
    GameRegistry.registerBlock(blockJournal, "journal_block");
    GameRegistry.registerBlock(blockDebug, "debug");

    // Tiles
    GameRegistry.registerTileEntity(TileEntityPipe.class, "pipe");
    GameRegistry.registerTileEntity(TileEntityMortar.class, "mortar");
    GameRegistry.registerTileEntity(TileEntityBrewBarrel.class, "brew_barrel");

    proxy.registerRenders();
  }

  private void registerRuin(ResourceLocation loc) {
    try (InputStream in = proxy.getClient()
                               .getResourceManager()
                               .getResource(loc)
                               .getInputStream()) {
      this.register(gson.fromJson(new InputStreamReader(in), Ruin.class));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Mod.EventHandler
  public void init(FMLInitializationEvent e) {
    NetworkRegistry.INSTANCE.registerGuiHandler(instance, instance);

    proxy.registerColors();

    String[] ruins = new String[]{
    "basic"
    };
    for (String ruin : ruins) this.registerRuin(new ResourceLocation("herbarium", "ruins/" + ruin + ".json"));

    String[] pages = new String[]{
    "Commentarium"
    };
    for (String page : pages) {
      try (InputStream is = Herbarium.proxy.getClient()
                                           .getResourceManager()
                                           .getResource(new ResourceLocation("herbarium", "pages/" + page + ".json"))
                                           .getInputStream()) {
        this.register(Herbarium.gson.fromJson(new InputStreamReader(is), IPage.class));
      } catch (Exception ex) {
        throw new RuntimeException(ex);
      }
    }
  }

  @Mod.EventHandler
  public void postInit(FMLPostInitializationEvent e) {
    HerbariumNetwork.init();

    MinecraftForge.EVENT_BUS.register(HerbariumApi.PAGE_TRACKER);
    MinecraftForge.EVENT_BUS.register(HerbariumApi.EFFECT_TRACKER);
    MinecraftForge.EVENT_BUS.register(new RuinGenerator());
    MinecraftForge.EVENT_BUS.register(new KeyHandler());

    MinecraftForge.EVENT_BUS.register(new BiomeSpecificGeneration(blockAlstromeria, Biomes.PLAINS, Biomes.RIVER, Biomes.FOREST_HILLS));
    MinecraftForge.EVENT_BUS.register(new BiomeSpecificGeneration(blockBlueAnemone, Biomes.OCEAN, Biomes.RIVER, Biomes.DEEP_OCEAN, Biomes.BEACH));
    MinecraftForge.EVENT_BUS.register(new BiomeSpecificGeneration(blockBelladonna, Biomes.SWAMPLAND, Biomes.ROOFED_FOREST));
    MinecraftForge.EVENT_BUS.register(new BiomeSpecificGeneration(blockButtercup, Biomes.PLAINS, Biomes.EXTREME_HILLS, Biomes.EXTREME_HILLS_EDGE, Biomes.EXTREME_HILLS_WITH_TREES));
    MinecraftForge.EVENT_BUS.register(new BiomeSpecificGeneration(blockFire, Biomes.MESA, Biomes.MESA_ROCK, Biomes.MESA_CLEAR_ROCK, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.SAVANNA, Biomes.MUTATED_SAVANNA_ROCK));
    MinecraftForge.EVENT_BUS.register(new BiomeSpecificGeneration(blockLotus, Biomes.FOREST, Biomes.FOREST_HILLS));
    MinecraftForge.EVENT_BUS.register(new BiomeSpecificGeneration(blockLongEarIris, Biomes.JUNGLE, Biomes.JUNGLE_HILLS, Biomes.JUNGLE_EDGE));
    MinecraftForge.EVENT_BUS.register(new BiomeSpecificGeneration(blockTropicalBerries, Biomes.JUNGLE, Biomes.JUNGLE_HILLS, Biomes.JUNGLE_EDGE));
    MinecraftForge.EVENT_BUS.register(new BiomeSpecificGeneration(blockWinterLily, Biomes.TAIGA, Biomes.COLD_TAIGA, Biomes.COLD_TAIGA_HILLS, Biomes.FROZEN_OCEAN, Biomes.FROZEN_RIVER, Biomes.MUTATED_ICE_FLATS, Biomes.ICE_MOUNTAINS, Biomes.TAIGA_HILLS));

    MinecraftForge.EVENT_BUS.register(new BiomeSpecificCaveGeneration(Biomes.EXTREME_HILLS, blockCave, 30));

    proxy.init();

    EnumJournalChapters.init();
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
    e.registerServerCommand(new CommandBase() {
      @Override
      public String getCommandName() {
        return "effects_clear";
      }

      @Override
      public String getCommandUsage(ICommandSender sender) {
        return "effects_clear";
      }

      @Override
      public void execute(MinecraftServer server, ICommandSender sender, String[] args)
      throws CommandException {
        HerbariumApi.EFFECT_TRACKER.clearEffects(((EntityPlayer) sender));
        HerbariumApi.EFFECT_TRACKER.syncEffects(((EntityPlayer) sender));
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
  public IPageGroup getPageGroup(String uuid) {
    for (IPageGroup group : this.groups) {
      if (group.uuid()
               .equals(uuid)) {
        return group;
      }
    }

    return null;
  }

  @Override
  public List<IPageGroup> sortedGroups() {
    Collections.sort(this.groups, new Comparator<IPageGroup>() {
      @Override
      public int compare(IPageGroup iPageGroup, IPageGroup t1) {
        return Integer.compare(iPageGroup.ordinal(), t1.ordinal());
      }
    });
    return Collections.unmodifiableList(this.groups);
  }

  @Override
  public void register(IPage page) {
    this.pages.add(page);
  }

  @Override
  public void register(IPageGroup group) {
    for (IPageGroup g : this.groups) {
      if (group.uuid()
               .equals(g.uuid())) {
        return;
      }
    }

    this.groups.add(group);
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
  public List<IEffect> allEffects() {
    return Collections.unmodifiableList(this.effects);
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
  public void registerAllele(IAllele allele, IChromosomeType type) {
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

  @Override
  public boolean isGem(IBlockState state) {
    return this.gems.contains(state.getBlock());
  }

  @Override
  public void register(Block block) {
    this.gems.add(block);
  }
}