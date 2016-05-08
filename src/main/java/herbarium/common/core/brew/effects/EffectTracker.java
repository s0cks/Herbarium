package herbarium.common.core.brew.effects;

import herbarium.api.HerbariumApi;
import herbarium.api.INBTSavable;
import herbarium.api.brew.effects.IEffect;
import herbarium.api.brew.effects.IEffectTracker;
import herbarium.common.net.HerbariumNetwork;
import herbarium.common.net.PacketSyncEffects;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.SaveHandler;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class EffectTracker
implements IEffectTracker{
    public static final class PlayerEffectData
    implements INBTSavable{
        private static final String CURRENT_TAG = "Current";

        private final Map<IEffect, Long> current = new HashMap<>();

        @Override
        public void readFromNBT(NBTTagCompound comp) {
            this.current.clear();

            if(comp.hasKey(CURRENT_TAG)){
                NBTTagList effects = comp.getTagList(CURRENT_TAG, 10);
                for(int i = 0; i < effects.tagCount(); i++){
                    NBTTagCompound effectComp = effects.getCompoundTagAt(i);
                    this.current.put(HerbariumApi.EFFECT_MANAGER.getEffect(effectComp.getString("Effect")), effectComp.getLong("Start"));
                }
            }
        }

        @Override
        public void writeToNBT(NBTTagCompound comp) {
            NBTTagList effects = new NBTTagList();
            for(Map.Entry<IEffect, Long> entry : this.current.entrySet()){
                NBTTagCompound effectComp = new NBTTagCompound();
                effectComp.setString("Effect", entry.getKey().uuid());
                effectComp.setLong("Start", entry.getValue());
                effects.appendTag(effectComp);
            }
            comp.setTag(CURRENT_TAG, effects);
        }
    }

    private final Map<EntityPlayer, PlayerEffectData> data = new HashMap<>();

    public void setData(EntityPlayer player, PlayerEffectData data){
        this.data.put(player, data);
    }

    public PlayerEffectData getData(EntityPlayer player){
        return this.data.get(player);
    }

    @Override
    public boolean hasEffects(EntityPlayer player) {
        return this.data.containsKey(player);
    }

    @Override
    public boolean effectActive(EntityPlayer player, IEffect effect) {
        if(effect == null) return false;
        if(!this.data.containsKey(player)) return false;
        for(Map.Entry<IEffect, Long> entry : this.data.get(player).current.entrySet()){
            if(entry.getKey() == null) continue;
            if(entry.getKey().uuid().equals(effect.uuid())){
                return true;
            }
        }

        return false;
    }

    @Override
    public List<IEffect> getEffects(EntityPlayer player) {
        if(!this.data.containsKey(player)) return null;
        return new LinkedList<>(this.data.get(player).current.keySet());
    }

    @Override
    public void setEffects(EntityPlayer player, List<IEffect> effects) {
        if(!this.data.containsKey(player)) this.data.put(player, new PlayerEffectData());
        PlayerEffectData ped = this.data.get(player);
        ped.current.clear();
        for(IEffect effect : effects){
            effect.onDrink(player);
            ped.current.put(effect, Minecraft.getSystemTime());
        }
    }

    @Override
    public void syncEffects(EntityPlayer player) {
        HerbariumNetwork.INSTANCE.sendTo(new PacketSyncEffects(getData(player)), ((EntityPlayerMP) player));
    }

    @Override
    public void clearEffects(EntityPlayer player) {
        if(!this.data.containsKey(player)) return;
        this.data.get(player).current.clear();
    }

    @SubscribeEvent
    public void onBreakSpeed(PlayerEvent.BreakSpeed e){
        if(!this.data.containsKey(e.getEntityPlayer())) return;
        for(Map.Entry<IEffect, Long> entry : this.data.get(e.getEntityPlayer()).current.entrySet()){
            e.setNewSpeed(entry.getKey().breakSpeed(e.getEntityPlayer(), e.getOriginalSpeed()));
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e){
        if(!this.data.containsKey(e.player)) return;
        PlayerEffectData data = this.data.get(e.player);
        for(Map.Entry<IEffect, Long> entry : data.current.entrySet()){
            if(entry.getKey() == null) continue;
            long current = Minecraft.getSystemTime();
            if((current - entry.getValue()) >= entry.getKey().duration()){
                entry.getKey().onTimeout(e.player);
                data.current.remove(entry.getKey());
                continue;
            }
            entry.getKey().onTick(e.player);
        }
    }

    @SubscribeEvent
    public void onPlayerActiveBlock(PlayerInteractEvent.RightClickBlock e){
        if(!this.data.containsKey(e.getEntityPlayer())) return;
        for(Map.Entry<IEffect, Long> entry : this.data.get(e.getEntityPlayer()).current.entrySet()){
            IBlockState state = e.getWorld().getBlockState(e.getPos());
            entry.getKey().onActiveBlock(e.getEntityPlayer(), e.getPos(), state);
        }
    }

    @SubscribeEvent
    public void onPlayerJump(LivingEvent.LivingJumpEvent e){
        if(e.getEntity() instanceof EntityPlayer){
            EntityPlayer player = ((EntityPlayer) e.getEntity());
            if(!this.data.containsKey(player)) return;
            for(Map.Entry<IEffect, Long> entry : this.data.get(player).current.entrySet()){
                entry.getKey().onJump(player);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTargeted(LivingSetAttackTargetEvent e){
        if(e.getTarget() instanceof EntityPlayer){
            EntityPlayer player = ((EntityPlayer) e.getTarget());
            if(!this.data.containsKey(player)) return;
            for(Map.Entry<IEffect, Long> entry : this.data.get(player).current.entrySet()){
                entry.getKey().onTargeted(player, e.getEntityLiving());
            }
        }
    }

    @SubscribeEvent
    public void onPlayerLoad(PlayerEvent.LoadFromFile e){
        this.loadPlayerEffectData(e.getEntityPlayer());
    }

    @SubscribeEvent
    public void onPlayerSave(PlayerEvent.SaveToFile e){
        this.savePlayerEffectData(e.getEntityPlayer());
    }

    @SubscribeEvent
    public void onPlayerLogIn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent e){
        if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER){
            this.syncEffects(e.player);
        }
    }

    private void loadPlayerEffectData(EntityPlayer player){
        IPlayerFileData nbtManager = FMLCommonHandler.instance().getMinecraftServerInstance()
                                              .worldServerForDimension(0)
                                              .getSaveHandler().getPlayerNBTManager();
        SaveHandler handler = ((SaveHandler) nbtManager);
        File dir = ObfuscationReflectionHelper.getPrivateValue(SaveHandler.class, handler, "playersDirectory", "field_75771_c");
        File file = new File(dir, player.getName() + ".effects");
        NBTTagCompound comp = this.load(file);
        PlayerEffectData data = new PlayerEffectData();
        data.readFromNBT(comp);
        this.data.put(player, data);
    }

    private void savePlayerEffectData(EntityPlayer player){
        IPlayerFileData nbtManager = FMLCommonHandler.instance().getMinecraftServerInstance()
                                                     .worldServerForDimension(0)
                                                     .getSaveHandler().getPlayerNBTManager();
        SaveHandler handler = ((SaveHandler) nbtManager);
        File dir = ObfuscationReflectionHelper.getPrivateValue(SaveHandler.class, handler, "playersDirectory", "field_75771_c");
        File file = new File(dir, player.getName() + ".effects");
        NBTTagCompound comp = new NBTTagCompound();
        this.data.get(player).writeToNBT(comp);
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