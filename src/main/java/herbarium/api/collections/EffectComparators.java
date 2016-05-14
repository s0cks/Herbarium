package herbarium.api.collections;

import herbarium.api.brew.effects.IEffect;

import java.util.Comparator;

public enum EffectComparators
    implements Comparator<IEffect> {
  UUID(){
    @Override
    public int compare(IEffect t0, IEffect t1) {
      return t0.uuid().compareTo(t1.uuid());
    }
  },
  TYPE(){
    @Override
    public int compare(IEffect t0, IEffect t1) {
      return Integer.compare(t0.type().ordinal(), t1.type().ordinal());
    }
  };
}