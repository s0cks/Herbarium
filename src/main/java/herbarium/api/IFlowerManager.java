package herbarium.api;

public interface IFlowerManager{
    public IFlower getFlower(String uuid);
    public void register(IFlower flower);
}