package herbarium.common;

import herbarium.api.HerbariumApi;
import herbarium.api.commentarium.IPage;
import herbarium.api.commentarium.IPageManager;
import herbarium.client.gui.GuiJournal;
import herbarium.common.blocks.BlockAlstromeria;
import herbarium.common.blocks.BlockDebug;
import herbarium.common.core.brew.BrewLevelManager;
import herbarium.common.core.brew.PlayerBrewLevel;
import herbarium.common.core.commentarium.PageBuilder;
import herbarium.common.core.commentarium.PageTracker;
import herbarium.common.items.ItemJournal;
import herbarium.common.items.ItemPage;
import herbarium.common.net.HerbariumNetwork;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
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
import java.util.Set;

@Mod(modid = "herbarium", name = "Herbarium", version = "0.0.0.0", dependencies = "required-after:Forge@[1.8.9-11.15.1.1722,)")
public final class Herbarium
implements IPageManager,
           IGuiHandler{
    @Mod.Instance("herbarium")
    public static Herbarium instance;

    @SidedProxy(clientSide = "herbarium.client.ClientProxy", serverSide = "herbarium.common.CommonProxy")
    public static CommonProxy proxy;

    // Items
    public static final Item itemJournal = new ItemJournal()
            .setCreativeTab(CreativeTabs.tabBrewing)
            .setUnlocalizedName("herba_commentarium")
            .setMaxStackSize(1);
    public static final Item itemPage = new ItemPage()
            .setCreativeTab(CreativeTabs.tabBrewing)
            .setUnlocalizedName("herbar_page")
            .setMaxStackSize(1);

    // Blocks
    public static final Block blockAlstromeria = new BlockAlstromeria()
            .setCreativeTab(CreativeTabs.tabBrewing)
            .setUnlocalizedName("herba_alstromeria");
    public static final Block blockDebug = new BlockDebug()
            .setCreativeTab(CreativeTabs.tabBrewing)
            .setUnlocalizedName("herba_debug");

    // GUIs
    public static final byte GUI_JOURNAL = 0x1;

    private final Set<IPage> pages = new HashSet<>();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e){
        HerbariumApi.PAGE_MANAGER = this;
        HerbariumApi.PAGE_TRACKER = new PageTracker();

        this.register(new PageBuilder().setTitle("Contents").build());
        this.register(new PageBuilder().setTitle("Alstromeria").build());

        // Items
        GameRegistry.registerItem(itemJournal, "journal");
        GameRegistry.registerItem(itemPage, "page");

        // Blocks
        GameRegistry.registerBlock(blockAlstromeria, "alstromeria");
        GameRegistry.registerBlock(blockDebug, "debug");

        proxy.registerRenders();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e){
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, instance);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e){
        HerbariumNetwork.init();

        MinecraftForge.EVENT_BUS.register(BrewLevelManager.INSTANCE);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent e){
        e.registerServerCommand(new CommandBase() {
            @Override
            public String getCommandName() {
                return "brew_level";
            }

            @Override
            public String getCommandUsage(ICommandSender sender) {
                return "brew_level";
            }

            @Override
            public void processCommand(ICommandSender sender, String[] args)
            throws CommandException {
                PlayerBrewLevel level = PlayerBrewLevel.get(((EntityPlayer) sender));
                ((EntityPlayer) sender).addChatComponentMessage(new ChatComponentText(level.get().name()));
            }
        });
        e.registerServerCommand(new CommandBase() {
            @Override
            public String getCommandName() {
                return "pages_clear";
            }

            @Override
            public String getCommandUsage(ICommandSender sender) {
                return "pages_clear";
            }

            @Override
            public void processCommand(ICommandSender sender, String[] args)
            throws CommandException {
                PageTracker.set(((EntityPlayer) sender), new HashSet<IPage>());
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
        for(IPage page : this.pages){
            if(page.uuid().equals(uuid)){
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
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch(ID){
            case GUI_JOURNAL: return new GuiJournal(player);
            default: return null;
        }
    }
}