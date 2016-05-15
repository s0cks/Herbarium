package herbarium.api.botany;

public interface IFlowerFactory {
  public IAlleleFlowerSpeciesBuilder createSpecies(String uuid, boolean dominant);
}