package herbarium.api.brew.effects;

import net.minecraft.entity.player.EntityPlayer;

public interface IEffect{
    public String uuid();
    public void onTick(EntityPlayer player);
    public void onDrink(EntityPlayer player);
    public void onTimeout(EntityPlayer player);
    public long duration();
}