package herbarium.api.commentarium;

import net.minecraft.entity.player.EntityPlayer;

import java.util.Set;

public interface IPageTracker{
    public IPage unlearnedPage(EntityPlayer player);
    public boolean learned(EntityPlayer player, IPage page);
    public boolean learn(EntityPlayer player, IPage page);
    public boolean unlearn(EntityPlayer player, IPage page);
    public Set<IPage> allLearned(EntityPlayer player);
    public void sync(EntityPlayer player);
}