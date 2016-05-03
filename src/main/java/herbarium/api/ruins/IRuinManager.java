package herbarium.api.ruins;

public interface IRuinManager{
    public void register(IRuin ruin);
    public IRuin getRuin(String uuid);
}