package herbarium.common.lib.genetics;

import herbarium.api.genetics.IAllele;
import herbarium.api.genetics.IGenome;
import herbarium.api.genetics.IIndividual;

public interface IGeneticDefinition{
    public IAllele[] getTemplate();
    public IGenome getGenome();
    public IIndividual getIndividual();
}