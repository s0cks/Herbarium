package herbarium.common.core.genetics.alleles;

import herbarium.api.EnumHumidity;
import herbarium.api.EnumTemperature;
import herbarium.api.genetics.alleles.IAlleleSpecies;

public abstract class AlleleSpecies
extends Allele
implements IAlleleSpecies {
    private EnumTemperature temperature = EnumTemperature.NORMAL;
    private EnumHumidity humidity = EnumHumidity.NORMAL;

    protected AlleleSpecies(String uuid, boolean dominant) {
        super(uuid, dominant);
    }

    @Override
    public EnumHumidity humidity() {
        return this.humidity;
    }

    @Override
    public EnumTemperature temperature() {
        return this.temperature;
    }
}