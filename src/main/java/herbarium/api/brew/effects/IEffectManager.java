package herbarium.api.brew.effects;

import java.util.List;

public interface IEffectManager {
  public void register(IEffect effect);
  public List<IEffect> allEffects();
  public IEffect getEffect(String uuid);
}