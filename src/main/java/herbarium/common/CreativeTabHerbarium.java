package herbarium.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public final class CreativeTabHerbarium
extends CreativeTabs {
  public CreativeTabHerbarium() {
    super("herbarium");
  }

  @Override
  public Item getTabIconItem() {
    return HerbariumItems.itemJournal;
  }
}