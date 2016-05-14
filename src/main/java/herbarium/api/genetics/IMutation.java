package herbarium.api.genetics;

import herbarium.api.genetics.alleles.IAlleleSpecies;

public interface IMutation {
  public ISpecies species();

  public IAlleleSpecies allele0();

  public IAlleleSpecies allele1();

  public IAllele[] template();

  public float baseChance();
}