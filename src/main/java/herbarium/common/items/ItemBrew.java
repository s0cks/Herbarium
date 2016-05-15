package herbarium.common.items;

import herbarium.api.HerbariumApi;
import herbarium.api.brew.EnumBrewType;
import herbarium.api.brew.IBrew;
import herbarium.api.brew.effects.IEffect;
import herbarium.common.core.NBTHelper;
import herbarium.common.core.brew.Brew;
import herbarium.common.core.brew.effects.effect.RemedyEffects;
import herbarium.common.core.brew.effects.effect.SpiritEffects;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.List;

public final class ItemBrew
extends Item {
  private static final String BREW_TAG = "Brew";

  public ItemBrew() {
    this.setHasSubtypes(true);
    this.setMaxDamage(0);
  }

  public static void setBrew(ItemStack stack, IBrew brew) {
    NBTTagCompound comp = NBTHelper.getCompound(stack);
    if (comp == null) comp = new NBTTagCompound();
    NBTTagCompound brewComp = new NBTTagCompound();
    brew.writeToNBT(brewComp);
    comp.setTag(BREW_TAG, brewComp);
    if (!stack.hasTagCompound()) stack.setTagCompound(comp);
  }

  public static IBrew getBrew(ItemStack stack) {
    NBTTagCompound comp = NBTHelper.getCompound(stack);
    if (comp == null) return null;
    if (!comp.hasKey(BREW_TAG)) return null;
    NBTTagCompound brewComp = comp.getCompoundTag(BREW_TAG);
    IBrew brew = new Brew();
    brew.readFromNBT(brewComp);
    return brew;
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
    IBrew brew = getBrew(itemStackIn);
    if (brew != null && !worldIn.isRemote) {
      HerbariumApi.EFFECT_TRACKER.setEffects(playerIn, brew.effects());
      HerbariumApi.EFFECT_TRACKER.syncEffects(playerIn);
    } else {
      Brew b = new Brew();
      b.effects.add(SpiritEffects.ORIENTATION_LOSS);
      b.effects.add(RemedyEffects.WATER_WALKING);
      b.effects.add(SpiritEffects.LUCKY);
      setBrew(itemStackIn, b);
    }
    return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {
    IBrew brew = getBrew(stack);
    if (brew != null) {
      return "item.herbarium.brew." + brew.computeBrewType()
                                          .name()
                                          .toLowerCase();
    }
    return "item.herbarium.brew";
  }

  @Override
  public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
    IBrew brew = getBrew(stack);
    if (brew != null) {
      for (IEffect effect : brew.effects()) {
        if (effect == null) continue;
        tooltip.add(effect.uuid());
      }
    }
  }

  @Override
  public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
    for (int i = 0; i < EnumBrewType.values().length; i++) {
      subItems.add(new ItemStack(itemIn, 1, i));
    }
  }

  public static final class BrewColorizer
  implements IItemColor {
    @Override
    public int getColorFromItemstack(ItemStack stack, int tintIndex) {
      IBrew brew = ItemBrew.getBrew(stack);
      if (brew != null && tintIndex == 1) {
        switch (brew.computeBrewType()) {
          case REMEDY:
            return 0xFF0000;
          case VENOM:
            return 0x00FF00;
          case SPIRIT:
            return 0xF79400;
        }
      }
      return 0xFFFFFF;
    }
  }
}