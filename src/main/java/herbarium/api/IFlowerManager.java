package herbarium.api;

import herbarium.api.botany.IFlower;

public interface IFlowerManager {
  public IFlower getFlower(String uuid);

  public void register(IFlower flower);
}