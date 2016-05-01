package herbarium.api.genetics;

public interface IChromosomeType{
    public Class<? extends IAllele> getAlleleClass();
    public String getLocalizedName();
    public ISpecies getSpecies();
    public int ordinal();
}