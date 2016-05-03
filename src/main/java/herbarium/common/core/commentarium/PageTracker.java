package herbarium.common.core.commentarium;

import herbarium.api.HerbariumApi;
import herbarium.api.commentarium.IPage;
import herbarium.api.commentarium.IPageTracker;
import herbarium.common.net.HerbariumNetwork;
import herbarium.common.net.PacketSyncPageData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.SaveHandler;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class PageTracker
implements IPageTracker{
    private static final class PageData{
        private static final String PAGES_TAG = "Pages";

        private final Set<IPage> pages;

        public PageData(Set<IPage> pages){
            this.pages = pages;
        }

        public void readFromNBT(NBTTagCompound comp){
            this.pages.clear();
            if(comp.hasKey(PAGES_TAG)){
                NBTTagList pages = comp.getTagList(PAGES_TAG, 8);
                for(int i = 0; i < pages.tagCount(); i++){
                    this.pages.add(HerbariumApi.PAGE_MANAGER.get(pages.getStringTagAt(i)));
                }
            }
        }

        public void writeToNBT(NBTTagCompound comp){
            NBTTagList pages = new NBTTagList();
            for(IPage page : this.pages){
                pages.appendTag(new NBTTagString(page.uuid()));
            }
            comp.setTag(PAGES_TAG, pages);
        }
    }

    private static final Map<EntityPlayer, PageData> data = new HashMap<>();

    public static void set(EntityPlayer player, Set<IPage> pages){
        if(!data.containsKey(player)){
            data.put(player, new PageData(pages));
            return;
        }

        PageData pd = data.get(player);
        pd.pages.clear();
        pd.pages.addAll(pages);
    }

    public static PageData get(EntityPlayer player){
        if(!data.containsKey(player)) data.put(player, new PageData(new HashSet<IPage>()));
        return data.get(player);
    }

    public void loadPlayerData(EntityPlayer player){
        IPlayerFileData nbtManager = MinecraftServer.getServer()
                                                    .worldServerForDimension(0)
                                                    .getSaveHandler().getPlayerNBTManager();
        SaveHandler handler = ((SaveHandler) nbtManager);
        File dir = ObfuscationReflectionHelper.getPrivateValue(SaveHandler.class, handler, "playersDirectory", "field_75771_c");
        File file = new File(dir, player.getName() + ".commentarium");
        NBTTagCompound comp = this.load(file);
        PageData pageData = new PageData(new HashSet<IPage>());
        pageData.readFromNBT(comp);
        data.put(player, pageData);
    }

    public void savePlayerData(EntityPlayer player){
        IPlayerFileData nbtManager = MinecraftServer.getServer()
                                                    .worldServerForDimension(0)
                                                    .getSaveHandler().getPlayerNBTManager();
        SaveHandler handler = ((SaveHandler) nbtManager);
        File dir = ObfuscationReflectionHelper.getPrivateValue(SaveHandler.class, handler, "playersDirectory", "field_75771_c");
        File file = new File(dir, player.getName() + ".commentarium");
        NBTTagCompound comp = this.load(file);
        data.get(player).writeToNBT(comp);
        this.save(comp, file);
    }

    private NBTTagCompound load(File file){
        try{
            NBTTagCompound comp = new NBTTagCompound();
            if(file != null && file.exists()){
                try(FileInputStream fis = new FileInputStream(file)){
                    comp = CompressedStreamTools.readCompressed(fis);
                }
            }
            return comp;
        } catch(Exception ex){
            ex.printStackTrace(System.err);
            return new NBTTagCompound();
        }
    }

    private void save(NBTTagCompound comp, File file){
        try(FileOutputStream fos = new FileOutputStream(file)){
            file.createNewFile();
            CompressedStreamTools.writeCompressed(comp, fos);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public IPage unlearnedPage(EntityPlayer player) {
        Set<IPage> all = HerbariumApi.PAGE_MANAGER.all();
        PageData data = get(player);
        it:
        for(IPage page0 : all){
            for(IPage page1 : data.pages){
               if(page0.uuid().equals(page1.uuid())){
                   continue it;
               }
            }
            return page0;
        }

        return null;
    }

    @Override
    public boolean learned(EntityPlayer player, IPage page) {
        return get(player).pages.contains(page);
    }

    @Override
    public boolean learn(EntityPlayer player, IPage page) {
        if(learned(player, page)) return false;
        get(player).pages.add(page);
        return true;
    }

    @Override
    public boolean unlearn(EntityPlayer player, IPage page) {
        if(!learned(player, page)) return false;
        get(player).pages.remove(page);
        return true;
    }

    @Override
    public Set<IPage> allLearned(EntityPlayer player) {
        return get(player).pages;
    }

    @Override
    public void sync(EntityPlayer player) {
        HerbariumNetwork.INSTANCE.sendTo(new PacketSyncPageData(get(player).pages), ((EntityPlayerMP) player));
    }

    @SubscribeEvent
    public void onPlayerLoad(PlayerEvent.LoadFromFile e){
        loadPlayerData(e.entityPlayer);
    }

    @SubscribeEvent
    public void onPlayerSave(PlayerEvent.SaveToFile e){
        savePlayerData(e.entityPlayer);
    }

    @SubscribeEvent
    public void onPlayerLogIn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent e){
        if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER){
            sync(e.player);
        }
    }
}