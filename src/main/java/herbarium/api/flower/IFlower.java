package herbarium.api.flower;

import herbarium.api.genetics.IIndividual;

public interface IFlower
extends IIndividual{
    public void mate(IFlower flower);
    public IFlowerGenome getGenome();
    public IFlowerGenome getMate();
    public boolean isPureBred(EnumFlowerChromosome chromosome);
}