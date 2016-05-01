package herbarium.common.lib.genetics;

import herbarium.api.genetics.IAllele;
import herbarium.api.genetics.IClassification;

public interface IBranchDefinition{
    public IAllele[] getTemplate();
    public IClassification getBranch();
}