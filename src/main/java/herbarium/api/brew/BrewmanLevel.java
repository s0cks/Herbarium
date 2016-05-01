package herbarium.api.brew;

public enum BrewmanLevel{
    APPRENTICE(0.10F, 0.10F),
    ADEPT(0.30F, 0.20F),
    EXPERT(0.50F, 0.30F),
    MASTER(0.70F, 100.0F);

    private final float complexityBalance;
    private final float complexityMax;

    private BrewmanLevel(float complexityBalance, float complexityMax) {
        this.complexityBalance = complexityBalance;
        this.complexityMax = complexityMax;
    }

    public float complexityBalancer(){
        return this.complexityBalance;
    }

    public float complexityMax(){
        return this.complexityMax;
    }

    public static BrewmanLevel next(BrewmanLevel level){
        if(level == MASTER){
            return MASTER;
        } else{
            return values()[level.ordinal() + 1];
        }
    }

    public static BrewmanLevel previous(BrewmanLevel level){
        if(level == APPRENTICE){
            return APPRENTICE;
        } else{
            return values()[level.ordinal() - 1];
        }
    }
}