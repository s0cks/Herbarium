package herbarium.api.brew;

public final class BrewStack{
    public final IBrew brew;
    public int amount;

    public BrewStack(IBrew brew, int amount){
        this.brew = brew;
        this.amount = amount;
    }
}