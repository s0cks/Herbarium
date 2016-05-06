package herbarium.common.core;

import herbarium.api.HerbariumApi;
import herbarium.api.IFlower;

public enum Flowers
implements IFlower {
    ALSTROMERIA(null),
    BELLADONNA(null),
    BLUE_ANEMONE(null),
    BLUEBERRY(null),
    BUTTERCUP(null),
    CAVE(null),
    DAISY(null),
    FIRE(null),
    LONG_EAR_IRIS(null),
    LOTUS(null),
    NETHER(null),
    TROPCIAL_BERRIES(null);
    
    Flowers(Object o) {

    }
    
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