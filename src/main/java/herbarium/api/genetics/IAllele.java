package herbarium.api.genetics;

public interface IAllele{
    public String getUUID();
    public String getLocalizedName();
    public boolean isDominant();
}