package herbarium.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import herbarium.api.HerbariumApi;
import herbarium.api.IGemTracker;
import herbarium.api.brew.effects.IEffect;
import herbarium.api.brew.effects.IEffectManager;
import herbarium.api.commentarium.pages.IPage;
import herbarium.api.commentarium.pages.IPageGroup;
import herbarium.api.commentarium.pages.IPageManager;
import herbarium.api.genetics.IAllele;
import herbarium.api.genetics.IAlleleManager;
import herbarium.api.genetics.IChromosomeType;
import herbarium.api.genetics.ISpecies;
import herbarium.client.font.HerbariumFontRenderer;
import herbarium.client.gui.GuiJournal;
import herbarium.common.core.KeyHandler;
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
import herbarium.common.net.HerbariumNetwork;
import herbarium.common.tiles.brewing.TileEntityBrewBarrel;
import herbarium.common.tiles.brewing.TileEntityCoalescer;
import herbarium.common.tiles.brewing.TileEntityCrucible;
import herbarium.common.tiles.brewing.TileEntityFermenter;
import herbarium.common.tiles.brewing.TileEntityMortar;
import herbarium.common.tiles.brewing.TileEntityPipe;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
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
        IAlleleManager,
        IGemTracker {
    @Mod.Instance("herbarium")
    public static Herbarium instance;
    public static final Random random = new Random();
    public static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(IPage.class, new JsonPageDeserializer())
            .create();
    public static final CreativeTabs tab = new CreativeTabHerbarium();
    // GUIs
    public static final byte GUI_JOURNAL = 0x1;

    static {
        HerbariumApi.FLOWER_FACTORY = new FlowerFactory();
        HerbariumApi.ALLELE_MANAGER = new AlleleManager();
        HerbariumApi.FLOWER_SPECIES = new FlowerSpecies();

        Flowers.initFlowers();
    }

    @SidedProxy(serverSide = "herbarium.common.CommonProxy", clientSide = "herbarium.client.ClientProxy")
    public static CommonProxy proxy;

    private final Set<IPage> pages = new HashSet<>();
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

        HerbariumItems.init();
        HerbariumBlocks.init();

        // Tiles
        GameRegistry.registerTileEntity(TileEntityPipe.class, "pipe");
        GameRegistry.registerTileEntity(TileEntityMortar.class, "mortar");
        GameRegistry.registerTileEntity(TileEntityBrewBarrel.class, "brew_barrel");
        GameRegistry.registerTileEntity(TileEntityCrucible.class, "crucible");
        GameRegistry.registerTileEntity(TileEntityCoalescer.class, "coalescer");
        GameRegistry.registerTileEntity(TileEntityFermenter.class, "fermenter");

        proxy.preInit();
        proxy.registerRenders();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, instance);

        proxy.init();
        proxy.registerColors();

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
        MinecraftForge.EVENT_BUS.register(new KeyHandler());

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