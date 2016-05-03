package herbarium.api.brew;

import java.util.Random;

public class Effects{
    private static final Random rand = new Random();

    private final IEffect alcoholic;
    private final IEffect posion;
    private final IEffect potion;

    public Effects(IEffect alcoholic, IEffect posion, IEffect potion) {
        this.alcoholic = alcoholic;
        this.posion = posion;
        this.potion = potion;
    }

    public IEffect get(EffectType type){
        switch(type){
            case ALCOHOLIC: return this.alcoholic;
            case POISON: return this.posion;
            case POTION: return this.potion;
            default: return null;
        }
    }

    // Biased towards Alcoholic
    public IEffect random(){
        for(EffectType type : EffectType.values()){
            if (rand.nextBoolean()) {
                return get(type);
            }
        }

        return get(EffectType.ALCOHOLIC);
    }
}