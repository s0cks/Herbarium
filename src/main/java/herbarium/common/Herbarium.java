package herbarium.common;

import herbarium.api.HerbariumApi;
import herbarium.api.commentarium.IPage;
import herbarium.api.commentarium.IPageGenerator;
import herbarium.api.commentarium.IPageManager;
import herbarium.common.core.brew.BrewLevelManager;
import herbarium.common.core.brew.PlayerBrewLevel;
import herbarium.common.core.commentarium.DefaultPageGenerator;
import herbarium.common.core.commentarium.DefaultPages;
import herbarium.common.items.ItemJournal;
import herbarium.common.items.ItemPage;
import herbarium.common.net.HerbariumNetwork;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Mod(modid = "herb", name = "Herbarium", version = "0.0.0.0", dependencies = "required-after:Forge@[1.8.9-11.15.1.1722,)")
public final class Herbarium
implements IPageManager{
    @Mod.Instance("herb")
    public static Herbarium instance;

    @SidedProxy(clientSide = "herbarium.client.ClientProxy", serverSide = "herbarium.common.CommonProxy")
    public static CommonProxy proxy;

    public static final Item itemJournal = new ItemJournal()
            .setCreativeTab(CreativeTabs.tabBrewing)
            .setUnlocalizedName("herba_commentarium")
            .setMaxStackSize(1);
    public static final Item itemPage = new ItemPage()
            .setCreativeTab(CreativeTabs.tabBrewing)
            .setUnlocalizedName("herba_commentarium_page")
            .setMaxStackSize(1);

    private final Set<IPage> pages = new HashSet<>();
    private IPageGenerator generator;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e){
        HerbariumApi.PAGE_MANAGER = this;
        for(DefaultPages page : DefaultPages.values()){
            System.out.println("Registering Page: " + page);
            register(page);
        }

        GameRegistry.registerItem(itemJournal, "herba_commentarium");
        GameRegistry.registerItem(itemPage, "herba_commentarium_page");
        proxy.registerRenders();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e){
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e){
        this.generator = new DefaultPageGenerator(this);
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
    }

    @Override
    public IPageGenerator generator() {
        return this.generator;
    }

    @Override
    public Set<IPage> all() {
        return Collections.unmodifiableSet(this.pages);
    }

    @Override
    public IPage get(String uuid) {
        for(IPage page : this.pages){
            System.out.println(page.uuid() + " ==? " + uuid);
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
}