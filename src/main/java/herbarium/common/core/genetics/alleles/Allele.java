package herbarium.common.core.genetics.alleles;

import herbarium.api.genetics.IAllele;

public abstract class Allele
implements IAllele {
    private final String uuid;
    private final boolean dominant;

    protected Allele(String uuid, boolean dominant) {
        this.uuid = uuid;
        this.dominant = dominant;
    }

    @Override
    public String uuid() {
        return this.uuid;
    }

    @Override
    public boolean dominant() {
        return this.dominant;
    }

    @Override
    public String unlocalizedName() {
        return "flowers.allele." + this.uuid + ".name";
    }
}