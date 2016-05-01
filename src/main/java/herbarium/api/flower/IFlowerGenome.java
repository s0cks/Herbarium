package herbarium.api.flower;

import herbarium.api.genetics.IGenome;

public interface IFlowerGenome
extends IGenome {
    public IAlleleFlowerSpecies getPrimary();
    public IAlleleFlowerSpecies getSecondary();
    public int getLifespan();
    public boolean isNocturnal();
}