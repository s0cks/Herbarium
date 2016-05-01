package herbarium.api.flower;

import herbarium.api.genetics.IClassification;

public interface IFlowerFactory{
    public IAlleleFlowerSpeciesBuilder createSpecies(String uuid, String unlocalizedName, boolean dominant, IClassification branch);
}