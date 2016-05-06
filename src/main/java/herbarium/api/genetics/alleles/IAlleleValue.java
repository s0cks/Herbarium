package herbarium.api.genetics.alleles;

import herbarium.api.genetics.IAllele;

public interface IAlleleValue<T>
extends IAllele{
    public T get();
}