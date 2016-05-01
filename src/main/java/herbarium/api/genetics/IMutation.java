package herbarium.api.genetics;

import herbarium.api.genetics.allele.IAlleleSpecies;

public interface IMutation{
    public IAllele[] getTemplate();
    public IAllele getPartner(IAllele allele);
    public IAlleleSpecies getAllele1();
    public IAlleleSpecies getAllele0();
    public ISpecies getSpecies();
    public float getBaseChance();
    public boolean isPartner(IAllele allele);
    public boolean isSecret();
}