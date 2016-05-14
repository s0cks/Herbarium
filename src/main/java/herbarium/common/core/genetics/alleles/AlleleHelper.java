package herbarium.common.core.genetics.alleles;

import herbarium.api.genetics.IAllele;
import herbarium.api.genetics.IChromosomeType;

public final class AlleleHelper{
  public static <T extends Enum<T> & IChromosomeType> void set(IAllele[] alleles, T type, IAllele allele){
    alleles[type.ordinal()] = allele;
  }
}