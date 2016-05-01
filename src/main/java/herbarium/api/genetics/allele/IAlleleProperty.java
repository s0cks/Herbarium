package herbarium.api.genetics.allele;

import herbarium.api.genetics.IAllele;

public interface IAlleleProperty<A extends IAlleleProperty<A>>
extends IAllele, Comparable<A>{

}