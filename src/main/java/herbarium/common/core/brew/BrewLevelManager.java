package herbarium.common.core.brew;

import herbarium.common.net.HerbariumNetwork;
import herbarium.common.net.PacketSyncBrewmanLevel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
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

public enum BrewLevelManager{
    INSTANCE;

    public void loadPlayerData(EntityPlayer player){
        IPlayerFileData nbtManager = MinecraftServer.getServer()
                                                    .worldServerForDimension(0)
                                                    .getSaveHandler().getPlayerNBTManager();
        SaveHandler handler = ((SaveHandler) nbtManager);
        File dir = ObfuscationReflectionHelper.getPrivateValue(SaveHandler.class, handler, "playersDirectory", "field_75771_c");
        File file = new File(dir, player.getName() + ".blevel");
        NBTTagCompound comp = this.load(file);
        PlayerBrewLevel level = new PlayerBrewLevel();
        level.readFromNBT(comp);
        PlayerBrewLevel.put(player, level);
    }

    public void savePlayerData(EntityPlayer player){
        IPlayerFileData nbtManager = MinecraftServer.getServer()
                                                    .worldServerForDimension(0)
                                                    .getSaveHandler().getPlayerNBTManager();
        SaveHandler handler = ((SaveHandler) nbtManager);
        File dir = ObfuscationReflectionHelper.getPrivateValue(SaveHandler.class, handler, "playersDirectory", "field_75771_c");
        File file = new File(dir, player.getName() + ".blevel");
        NBTTagCompound comp = new NBTTagCompound();
        PlayerBrewLevel.get(player).writeToNBT(comp);
        this.save(comp, file);
    }

    public void sync(EntityPlayer player){
        if(Side.SERVER == FMLCommonHandler.instance().getEffectiveSide()){
            HerbariumNetwork.INSTANCE.sendTo(new PacketSyncBrewmanLevel(PlayerBrewLevel.get(player).get()), ((EntityPlayerMP) player));
        }
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
            HerbariumNetwork.INSTANCE.sendTo(new PacketSyncBrewmanLevel(PlayerBrewLevel.get(e.player).get()), ((EntityPlayerMP) e.player));
        }
    }
}