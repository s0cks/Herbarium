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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public final class PageTracker
implements IPageTracker{
    public PageTracker(){
        MinecraftForge.EVENT_BUS.register(this);
    }

    private static final class PageData{
        private static final String PAGE_DATA_TAG = "PageData";

        private final Set<IPage> pages;
        private final Queue<IPage> all = new LinkedList<>();

        public PageData(Set<IPage> pages){
            this.pages = pages;
            Set<IPage> all = HerbariumApi.PAGE_MANAGER.all();
            it:
            for(IPage page0 : all){
                for(IPage page1 : this.pages){
                    if(page0.uuid().equals(page1.uuid())){
                        continue it;
                    }
                }
                this.all.add(page0);
            }
            Collections.shuffle(((LinkedList<IPage>) this.all));
        }

        public void writeToNBT(NBTTagCompound comp){
            NBTTagList list = new NBTTagList();
            int i = 0;
            for(IPage page : this.pages){
                list.appendTag(new NBTTagString(page.uuid()));
            }
            comp.setTag(PAGE_DATA_TAG, list);
        }

        public void readFromNBT(NBTTagCompound comp){
            if(!comp.hasKey(PAGE_DATA_TAG)) return;
            this.pages.clear();
            NBTTagList list = comp.getTagList(PAGE_DATA_TAG, 10);
            for(int i = 0; i < list.tagCount(); i++){
                this.pages.add(HerbariumApi.PAGE_MANAGER.get(list.getStringTagAt(i)));
            }
        }

        public boolean has(IPage page){
            for(IPage p : this.pages){
                if(p.uuid().equals(page.uuid())){
                    return true;
                }
            }

            return false;
        }
    }

    private static final Map<EntityPlayer, PageData> data = new HashMap<>();

    public static void set(EntityPlayer player, Set<IPage> pages){
        data.put(player, new PageData(pages));
    }

    public static boolean has(EntityPlayer player){
        return data.get(player) != null;
    }

    @Override
    public IPage unlearnedPage(EntityPlayer player) {
        if(!has(player)) return null;
        if(data.get(player).all.isEmpty()) return null;
        return data.get(player).all.remove();
    }

    @Override
    public boolean learned(EntityPlayer player, IPage page) {
        return data.get(player).has(page);
    }

    @Override
    public boolean learn(EntityPlayer player, IPage page) {
        if(!has(player)) return false;
        if(learned(player, page)) return false;
        data.get(player).pages.add(page);
        return true;
    }

    @Override
    public boolean unlearn(EntityPlayer player, IPage page) {
        if(!has(player)) return false;
        if(!learned(player, page)) return false;
        data.get(player).pages.remove(page);
        return true;
    }

    @Override
    public Set<IPage> allLearned(EntityPlayer player) {
        if(!has(player)) return null;
        return data.get(player).pages;
    }

    @Override
    public void sync(EntityPlayer player) {
        HerbariumNetwork.INSTANCE.sendTo(new PacketSyncPageData(allLearned(player)), ((EntityPlayerMP) player));
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
}