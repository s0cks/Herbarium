package herbarium.common;

import herbarium.api.HerbariumApi;
import herbarium.api.commentarium.IPage;
import herbarium.api.commentarium.IPageGenerator;
import herbarium.api.commentarium.IPageManager;
import herbarium.common.core.commentarium.DefaultPageGenerator;
import herbarium.common.core.commentarium.DefaultPages;
import herbarium.common.items.ItemJournal;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
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

    private final Set<IPage> pages = new HashSet<>();
    private IPageGenerator generator;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e){
        HerbariumApi.PAGE_MANAGER = this;
        Collections.addAll(this.pages, DefaultPages.values());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e){
        GameRegistry.registerItem(itemJournal, "herba_commentarium");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e){
        this.generator = new DefaultPageGenerator(this);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent e){

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
    public void register(IPage page) {
        this.pages.add(page);
    }
}