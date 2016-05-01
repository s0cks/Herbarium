package herbarium.common.lib.genetics;

public class Branch
extends Classification{
    public Branch(String uuid, String scientific){
        super(EnumClassificationLevel.GENUS, uuid, scientific);
    }
}