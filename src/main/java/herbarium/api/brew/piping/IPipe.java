package herbarium.api.brew.piping;

import herbarium.api.brew.BrewStack;

public interface IPipe {
  public int drain(BrewStack stack, IPipe to);

  public int add(BrewStack stack, IPipe from);
}