package herbarium.common.core.brew;

import herbarium.api.brew.BrewmanLevel;
import herbarium.api.brew.EffectGraph;
import herbarium.api.brew.IBrew;
import herbarium.api.brew.IMixer;
import herbarium.api.brew.IEffect;

import java.util.Random;

public final class Mixer
        implements IMixer {
    protected final EffectGraph graph = new EffectGraph();
    private final Random random = new Random();

    public boolean canBrew(BrewmanLevel level){
        return this.graph.canBrew(this.random, level);
    }

    @Override
    public IBrew build(BrewmanLevel level) {
        this.graph.optimize();
        return new Brew(this, level);
    }

    @Override
    public IMixer add(IEffect effect) {
        this.graph.add(effect);
        this.graph.optimize();
        return this;
    }
}