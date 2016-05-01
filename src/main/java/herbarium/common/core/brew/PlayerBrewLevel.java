package herbarium.common.core.brew;

import herbarium.api.brew.BrewmanLevel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.Map;

public final class PlayerBrewLevel{
    private static final String LEVEL_TAG = "BREWMAN_LEVEL";

    private BrewmanLevel level = BrewmanLevel.APPRENTICE;

    public void writeToNBT(NBTTagCompound comp){
        if(this.level != null){
            comp.setString(LEVEL_TAG, level.name());
        }
    }

    public void readFromNBT(NBTTagCompound comp){
        if(comp.hasKey(LEVEL_TAG)){
            this.level = BrewmanLevel.valueOf(comp.getString(LEVEL_TAG));
        }
    }

    public BrewmanLevel get(){
        return this.level;
    }

    public void set(BrewmanLevel level){
        this.level = level;
    }

    private static final Map<EntityPlayer, PlayerBrewLevel> brewLevels = new HashMap<>();

    public static void put(EntityPlayer player, PlayerBrewLevel level){
        brewLevels.put(player, level);
    }

    public static PlayerBrewLevel get(EntityPlayer player){
        return brewLevels.get(player);
    }
}