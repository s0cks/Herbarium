package herbarium.api;

import herbarium.api.botany.IFlowerFactory;
import herbarium.api.botany.IFlowerSpecies;
import herbarium.api.brew.effects.IEffectManager;
import herbarium.api.brew.effects.IEffectTracker;
import herbarium.api.commentarium.journal.IJournalFactory;
import herbarium.api.commentarium.pages.IPageManager;
import herbarium.api.commentarium.pages.IPageTracker;
import herbarium.api.genetics.IAlleleManager;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

public final class HerbariumApi {
  public static final Item.ToolMaterial PESTLE_MATERIAL =
  EnumHelper.addToolMaterial("pestle", 0, 261, 1.0F, 3.0F, 0);
  // Pages
  public static IPageManager PAGE_MANAGER;
  public static IPageTracker PAGE_TRACKER;
  // Flowers
  public static IFlowerManager FLOWER_MANAGER;
  // Effects
  public static IEffectTracker EFFECT_TRACKER;
  public static IEffectManager EFFECT_MANAGER;
  // Genetics
  public static IAlleleManager ALLELE_MANAGER;
  public static IFlowerSpecies FLOWER_SPECIES;
  public static IFlowerFactory FLOWER_FACTORY;
  // Misc
  public static IGemTracker GEM_TRACKER;
  public static IHerbariumFontRenderer FONT_RENDERER;
  public static IJournalFactory JOURNAL_FACTORY;
}