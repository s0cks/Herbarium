package herbarium.api.brew.effects;

public interface IEffectManager{
    public void register(IEffect effect);
    public IEffect getEffect(String uuid);
}