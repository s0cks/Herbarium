package herbarium.common.core;

import herbarium.api.HerbariumApi;
import herbarium.api.IFlower;

public enum Flowers
implements IFlower {
    ALSTROMERIA,
    BELLADONNA,
    BLUE_ANEMONE,
    BLUEBERRY,
    BUTTERCUP,
    CAVE,
    DAISY,
    FIRE,
    LONG_EAR_IRIS,
    LOTUS,
    NETHER,
    TROPCIAL_BERRIES;

    public static void init(){
        for(Flowers flower : values()){
            HerbariumApi.FLOWER_MANAGER.register(flower);
        }
    }

    @Override
    public String uuid() {
        return "herba." + this.name().toLowerCase();
    }
}