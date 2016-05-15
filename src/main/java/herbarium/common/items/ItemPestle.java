package herbarium.common.items;

import com.google.common.collect.Sets;
import herbarium.api.HerbariumApi;
import herbarium.api.IPestle;
import net.minecraft.block.Block;
import net.minecraft.item.ItemTool;

public class ItemPestle
extends ItemTool
implements IPestle {
  public ItemPestle() {
    super(HerbariumApi.PESTLE_MATERIAL, Sets.<Block>newHashSet());
  }
}