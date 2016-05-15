package herbarium.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public final class ItemPaste
extends Item {
  public static final String[] names = new String[]{
  "alstromeria",
  "anemone",
  "belladonna",
  "blueberry_blossom",
  "buttercup",
  "cavern_bloom",
  "igneous_spear",
  "lancet_root",
  "spring_lotus",
  "tail_iris",
  "tropical_berries",
  "winter_lily"
  };

  public static final byte alstromeria = 0x0;
  public static final byte anemone = 0x1;
  public static final byte belladonna = 0x2;
  public static final byte blueberry_blossom = 0x3;
  public static final byte buttercup = 0x4;
  public static final byte cavern_bloom = 0x5;
  public static final byte igneous_spear = 0x7;
  public static final byte lancet_root = 0x8;
  public static final byte spring_lotus = 0x9;
  public static final byte tail_iris = 0xA;
  public static final byte tropical_berries = 0xB;
  public static final byte winter_lily = 0xC;

  public ItemPaste() {
    this.setHasSubtypes(true);
    this.setMaxDamage(0);
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {
    return "item.herbarium.paste." + names[stack.getItemDamage()];
  }

  @Override
  public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
    for (int i = 0; i < names.length; i++) {
      subItems.add(new ItemStack(itemIn, 1, i));
    }
  }
}