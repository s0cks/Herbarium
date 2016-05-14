package herbarium.common.core.brew;

import herbarium.api.HerbariumApi;
import herbarium.api.brew.EnumBrewType;
import herbarium.api.brew.IBrew;
import herbarium.api.brew.effects.IEffect;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class Brew
    implements IBrew {
  private static final String EFFECTS_TAG = "Effects";

  public final List<IEffect> effects = new LinkedList<>();

  @Override
  public List<IEffect> effects() {
    return Collections.unmodifiableList(this.effects);
  }

  @Override
  public EnumBrewType computeBrewType() {
    EnumBrewType lastType = EnumBrewType.VENOM;
    int lastCount = 0;
    for (EnumBrewType type : EnumBrewType.values()) {
      int count = this.effectsFor(type);
      if (count > lastCount) {
        lastCount = count;
        lastType = type;
      }
    }
    return lastType;
  }

  private int effectsFor(EnumBrewType type) {
    int count = 0;
    for (IEffect effect : this.effects) {
      if (effect == null) continue;
      if (effect.type()
                .equals(type)) {
        count++;
      }
    }

    return count;
  }

  @Override
  public void readFromNBT(NBTTagCompound comp) {
    this.effects.clear();
    if (comp.hasKey(EFFECTS_TAG)) {
      NBTTagList effects = comp.getTagList(EFFECTS_TAG, 8);
      for (int i = 0; i < effects.tagCount(); i++) {
        this.effects.add(HerbariumApi.EFFECT_MANAGER.getEffect(effects.getStringTagAt(i)));
      }
    }
  }

  @Override
  public void writeToNBT(NBTTagCompound comp) {
    NBTTagList effects = new NBTTagList();
    for (IEffect effect : this.effects) {
      effects.appendTag(new NBTTagString(effect.uuid()));
    }
    comp.setTag(EFFECTS_TAG, effects);
  }
}