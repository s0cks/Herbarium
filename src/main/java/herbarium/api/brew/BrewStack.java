package herbarium.api.brew;

public final class BrewStack{
    public final IBrew brew;
    public long amount;

    public BrewStack(IBrew brew){
        this.brew = brew;
    }
}