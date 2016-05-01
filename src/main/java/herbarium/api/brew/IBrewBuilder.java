package herbarium.api.brew;

public interface IBrewBuilder{
    public IBrew build();
    public IBrewBuilder add(IEffect effect);
}