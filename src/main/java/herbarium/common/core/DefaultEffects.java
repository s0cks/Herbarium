package herbarium.common.core;

import herbarium.api.brew.BrewmanLevel;
import herbarium.api.brew.EffectType;
import herbarium.api.brew.IEffect;
import net.minecraft.util.text.translation.I18n;

public enum DefaultEffects
implements IEffect {
    // Basic
    // Poisons
    FLAME(EffectType.POISON, BrewmanLevel.ADEPT) {
        @Override
        public boolean compatible(IEffect effect) {
            return effect.equals(FROST)
                || effect.equals(DRUNK);
        }

        @Override
        public IEffect combine(IEffect other) {
            if(other.equals(DRUNK)){
                return EXPLOSION;
            } else if(other.equals(FROST)){
                return FROST_BITE;
            }

            return null;
        }
    },
    FROST(EffectType.POISON, BrewmanLevel.APPRENTICE){
        @Override
        public boolean compatible(IEffect effect) {
            return effect.equals(FLAME);
        }

        @Override
        public IEffect combine(IEffect other) {
            return FROST_BITE;
        }
    },
    POSION(EffectType.POISON, BrewmanLevel.APPRENTICE){
        @Override
        public boolean compatible(IEffect effect) {
            return false;
        }

        @Override
        public IEffect combine(IEffect other) {
            return null;
        }
    },
    // Alcoholic
    DRUNK(EffectType.ALCOHOLIC, BrewmanLevel.ADEPT){
        @Override
        public boolean compatible(IEffect effect) {
            return effect.equals(FLAME);
        }

        @Override
        public IEffect combine(IEffect other) {
            return EXPLOSION;
        }
    },
    // Complex
    // Poisons
    FROST_BITE(EffectType.POISON, BrewmanLevel.MASTER){
        @Override
        public boolean compatible(IEffect effect) {
            return false;
        }

        @Override
        public IEffect combine(IEffect other) {
            return null;
        }
    },
    EXPLOSION(EffectType.POISON, BrewmanLevel.ADEPT){
        @Override
        public boolean compatible(IEffect effect) {
            return false;
        }

        @Override
        public IEffect combine(IEffect other) {
            return null;
        }
    };

    private final EffectType type;
    private final float complexity;

    private DefaultEffects(EffectType type, BrewmanLevel level){
        this.type = type;
        this.complexity = level.complexityMax();
    }

    @Override
    public String uuid() {
        return "herbarium." + this.name().toLowerCase();
    }

    @Override
    public String localizedName() {
        return I18n.translateToLocal("herbarium.effect." + this.name().toLowerCase());
    }

    @Override
    public boolean is(EffectType type) {
        return this.type.equals(type);
    }

    @Override
    public float complexity() {
        return this.complexity;
    }

    @Override
    public EffectType type() {
        return this.type;
    }
}