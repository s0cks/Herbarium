package herbarium.api.brew;

public interface IPipe{
    public int drain(BrewStack stack, IPipe to);
    public int add(BrewStack stack, IPipe from);
}