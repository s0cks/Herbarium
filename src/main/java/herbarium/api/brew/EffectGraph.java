package herbarium.api.brew;

import java.util.Random;

public final class EffectGraph{
    private final EffectSet effects = new EffectSet();

    public void add(IEffect effect){
        this.effects.add(effect);
    }

    public void optimize(){
        while(this.canonicalize());
    }

    public boolean isMajorly(EffectType type){
        int count = 0;
        for(IEffect effect : this.effects){
            if(effect.is(type)) count++;
        }

        return ((count / this.effects.size()) * 100.0F) > 49.9F;
    }

    public EffectType majority(){
        for(EffectType type : EffectType.values()){
            if(isMajorly(type)){
                return type;
            }
        }

        int last = 0;
        EffectType lastType = null;
        for(EffectType type : EffectType.values()){
            int count = this.count(type);
            if(count >= last) {
                last = count;
                lastType = type;
            }
        }

        return lastType;
    }

    public int count(EffectType type){
        int count = 0;
        for(IEffect effect : this.effects) {
            if(effect.is(type)) count++;
        }
        return count;
    }

    public boolean canBrew(Random rand, BrewmanLevel level){
        float complexity = this.computeComplexity(rand, level.complexityMax());
        return complexity < level.complexityBalancer();
    }

    public float computeComplexity(Random rand, float max){
        float maximum = 0.0F;
        float complexity = 0.0F;

        for(IEffect effect : this.effects){
            float comp = effect.complexity();

            if(comp > max && !rand.nextBoolean()){
                complexity -= comp;
                continue;
            }

            if(comp > max){
                continue;
            }

            complexity += comp;
            maximum += comp;
        }

        return 100.0F - (complexity < 0.0F ?
                                 0.0F :
                                 ((complexity / maximum) * 100.0F));
    }

    public boolean canonicalize(){
        boolean changed = false;

        for(int i = 0; i < this.effects.size(); i++){
            IEffect current = this.effects.get(i);

            for(int j = 0; j < this.effects.size(); j++){
                IEffect combinator = this.effects.get(j);
                if(combinator.compatible(current)){
                    this.effects.remove(j);
                    this.effects.remove(i);
                    this.effects.add(combinator.combine(current));
                    changed = true;
                }
            }
        }

        return changed;
    }
}