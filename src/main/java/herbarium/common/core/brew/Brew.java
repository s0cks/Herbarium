package herbarium.common.core.brew;

import herbarium.api.brew.BrewmanLevel;
import herbarium.api.brew.EffectSet;
import herbarium.api.brew.EffectType;
import herbarium.api.brew.IBrew;

public final class Brew
implements IBrew{
    private final EffectType type;
    private final EffectSet effects;

    protected Brew(Mixer builder, BrewmanLevel level){
        if(builder.canBrew(level)){
            this.type = EffectType.BOTCHED;
            this.effects = null;
        } else{
            this.effects = builder.graph.compile();
            this.type = builder.graph.majority();
        }
    }

    @Override
    public EffectType type(){
        return this.type;
    }

    @Override
    public EffectSet effects(){
        return this.effects;
    }
}