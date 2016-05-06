package herbarium.api.brew.effects;

import herbarium.api.brew.EnumBrewType;

public final class EffectSet{
    private final IEffect[] effects = new IEffect[3];

    public static EffectSet of(IEffect poison, IEffect potion, IEffect alcoholic){
        EffectSet set = new EffectSet();
        set.effects[EnumBrewType.POISON.ordinal()] = poison;
        set.effects[EnumBrewType.POTION.ordinal()] = potion;
        set.effects[EnumBrewType.ALCOHOLIC.ordinal()] = alcoholic;
        return set;
    }

    public IEffect get(EnumBrewType type){
        return this.effects[type.ordinal()];
    }

    public void set(EnumBrewType type, IEffect effect){
        this.effects[type.ordinal()] = effect;
    }
}