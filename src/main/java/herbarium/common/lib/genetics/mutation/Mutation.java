package herbarium.common.lib.genetics.mutation;

import herbarium.api.genetics.IAllele;
import herbarium.api.genetics.IMutation;
import herbarium.api.genetics.allele.IAlleleSpecies;

public abstract class Mutation
implements IMutation{
    private final int chance;
    private final IAlleleSpecies species0;
    private final IAlleleSpecies species1;
    private final IAllele[] template;

    private boolean secret = false;

    protected Mutation(IAlleleSpecies species0, IAlleleSpecies species1, IAllele[] template, int chance){
        this.species0 = species0;
        this.species1 = species1;
        this.template = template;
        this.chance = chance;
    }

    protected void setSecret(){
        this.secret = true;
    }

    @Override
    public IAllele[] getTemplate() {
        return this.template;
    }

    @Override
    public IAllele getPartner(IAllele allele) {
        return this.species0.getUUID().equals(allele.getUUID()) ?
                       this.species1 :
                       this.species0;
    }

    @Override
    public IAlleleSpecies getAllele1() {
        return this.species1;
    }

    @Override
    public IAlleleSpecies getAllele0() {
        return this.species0;
    }

    @Override
    public float getBaseChance() {
        return this.chance;
    }

    @Override
    public boolean isPartner(IAllele allele) {
        return this.species0.getUUID().equals(allele.getUUID()) ||
                       this.species1.getUUID().equals(allele.getUUID());
    }

    @Override
    public boolean isSecret() {
        return this.secret;
    }
}