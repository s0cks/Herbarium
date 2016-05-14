package herbarium.api.commentarium.journal;

import net.minecraft.entity.player.EntityPlayer;

public interface IJournalFactory{
  public IJournal create(EntityPlayer player);
}