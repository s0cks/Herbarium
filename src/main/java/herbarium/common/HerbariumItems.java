package herbarium.common;

import herbarium.common.items.ItemBrew;
import herbarium.common.items.ItemJournal;
import herbarium.common.items.ItemPage;
import herbarium.common.items.ItemPaste;
import herbarium.common.items.ItemPestle;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class HerbariumItems {
  public static final Item itemJournal = new ItemJournal()
                                         .setCreativeTab(Herbarium.tab)
                                         .setUnlocalizedName("journal")
                                         .setMaxStackSize(1)
                                         .setRegistryName(new ResourceLocation("herbarium", "journal"));
  public static final Item itemPage = new ItemPage()
                                      .setCreativeTab(Herbarium.tab)
                                      .setUnlocalizedName("page")
                                      .setMaxStackSize(1)
                                      .setRegistryName(new ResourceLocation("herbarium", "page"));
  public static final Item itemPestle = new ItemPestle()
                                        .setCreativeTab(Herbarium.tab)
                                        .setUnlocalizedName("pestle")
                                        .setMaxStackSize(1)
                                        .setRegistryName(new ResourceLocation("herbarium", "pestle"));
  public static final Item itemBrew = new ItemBrew()
                                      .setCreativeTab(Herbarium.tab)
                                      .setUnlocalizedName("brew")
                                      .setMaxStackSize(1)
                                      .setRegistryName(new ResourceLocation("herbarium", "brew"));
  public static final Item itemPaste = new ItemPaste()
                                       .setCreativeTab(Herbarium.tab)
                                       .setUnlocalizedName("paste")
                                       .setRegistryName(new ResourceLocation("herbarium", "paste"));

  public static void init() {
    GameRegistry.register(itemJournal);
    GameRegistry.register(itemPage);
    GameRegistry.register(itemPestle);
    GameRegistry.register(itemBrew);
    GameRegistry.register(itemPaste);
  }
}