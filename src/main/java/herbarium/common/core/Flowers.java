package herbarium.common.core;

import herbarium.api.HerbariumApi;
import herbarium.api.IFlower;
import herbarium.api.brew.Effects;

public enum Flowers
implements IFlower {
    ALSTROMERIA(new Effects(DefaultEffects.DRUNK, null, null)),
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
    TROPCIAL_BERRIES(new Effects(DefaultEffects.DRUNK, DefaultEffects.POSION, null));

    private final Effects effects;

    private Flowers(Effects effects) {
        this.effects = effects;
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

    @Override
    public Effects effects() {
        return this.effects;
    }
}