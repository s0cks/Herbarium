package herbarium.api.brew.effects;

import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public interface IEffectTracker{
    public boolean hasEffect(EntityPlayer player);
    public List<IEffect> getEffects(EntityPlayer player);
    public void setEffects(EntityPlayer player, List<IEffect> effects);
    public void syncEffects(EntityPlayer player);
}