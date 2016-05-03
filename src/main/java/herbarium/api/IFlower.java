package herbarium.api;

import herbarium.api.brew.Effects;

public interface IFlower {
    public String uuid();
    public String name();
    public Effects effects();
}