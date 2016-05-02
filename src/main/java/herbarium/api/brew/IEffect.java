package herbarium.api.brew;

public interface IEffect{
    public String uuid();
    public String localizedName();
    public boolean compatible(IEffect effect);
    public boolean is(EffectType type);
    public IEffect combine(IEffect other);
    public float complexity();
    public EffectType type();
}