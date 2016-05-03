package herbarium.api.ruins;

import java.util.Random;

public interface IRuinManager{
    public void register(IRuin ruin);
    public IRuin getRuin(String uuid);
    public IRuin getRandom(Random rand);
}