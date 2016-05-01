package herbarium.api.genetics;

import herbarium.api.genetics.allele.IAlleleArea;
import herbarium.api.genetics.allele.IAlleleBoolean;
import herbarium.api.genetics.allele.IAlleleFloat;
import herbarium.api.genetics.allele.IAlleleInt;

public interface IAlleleFactory{
    public IAlleleFloat createFloat(String cat, String name, float value, boolean isDominant, IChromosomeType... types);
    public IAlleleArea createArea(String cat, String name, int x, int y, int z, boolean isDominant, IChromosomeType... types);
    public IAlleleBoolean createBoolean(String cat, boolean value, boolean isDominant, IChromosomeType... types);
    public IAlleleInt createInt(String cat, String name, int value, boolean isDominant, IChromosomeType... types);
}