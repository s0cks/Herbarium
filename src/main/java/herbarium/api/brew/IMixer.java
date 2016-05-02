package herbarium.api.brew;

public interface IMixer {
    public IBrew build(BrewmanLevel level);
    public IMixer add(IEffect effect);
}