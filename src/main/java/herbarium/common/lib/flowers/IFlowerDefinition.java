package herbarium.common.lib.flowers;

import herbarium.api.flower.IFlower;
import herbarium.api.flower.IFlowerGenome;
import herbarium.common.lib.genetics.IGeneticDefinition;

public interface IFlowerDefinition
extends IGeneticDefinition{
    @Override public IFlowerGenome getGenome();
    @Override public IFlower getIndividual();
}