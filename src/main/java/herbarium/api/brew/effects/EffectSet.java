package herbarium.api.brew.effects;

public final class EffectSet{
    private final IEffect[] effects = new IEffect[3];

    public static final byte POISON_EFFECT = 0x0;
    public static final byte POTION_EFFECT = 0x1;
    public static final byte ALCOHOLIC_EFFECT = 0x2;

    public static EffectSet of(IEffect poison, IEffect potion, IEffect alcoholic){
        EffectSet set = new EffectSet();
        set.effects[POISON_EFFECT] = poison;
        set.effects[POTION_EFFECT] = potion;
        set.effects[ALCOHOLIC_EFFECT] = alcoholic;
        return set;
    }

    public IEffect get(int type){
        return this.effects[type];
    }
}