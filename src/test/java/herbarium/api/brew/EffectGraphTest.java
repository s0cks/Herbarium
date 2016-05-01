package herbarium.api.brew;

import java.util.Random;

public class EffectGraphTest {
    enum Effects
    implements IEffect{
        NONE {
            @Override
            public boolean compatible(IEffect effect) {
                return effect.equals(FROST);
            }

            @Override
            public boolean is(EffectType type) {
                return type == EffectType.ALCOHOLIC;
            }

            @Override
            public IEffect combine(IEffect other) {
                return FIRE;
            }

            @Override
            public float complexity(){
                return 0.1F;
            }
        },
        FIRE {
            @Override
            public boolean compatible(IEffect effect) {
                return false;
            }

            @Override
            public boolean is(EffectType type) {
                return EffectType.POISON == type;
            }

            @Override
            public IEffect combine(IEffect other) {
                return null;
            }

            @Override
            public float complexity() {
                return 0.2F;
            }
        },
        FROST {
            @Override
            public boolean compatible(IEffect effect) {
                return effect.equals(NONE);
            }

            @Override
            public boolean is(EffectType type) {
                return EffectType.POTION == type;
            }

            @Override
            public IEffect combine(IEffect other) {
                return FIRE;
            }

            @Override
            public float complexity() {
                return 0.1F;
            }
        },
        TEST(){
            @Override
            public boolean compatible(IEffect effect) {
                return false;
            }

            @Override
            public boolean is(EffectType type) {
                return EffectType.POTION == type;
            }

            @Override
            public IEffect combine(IEffect other) {
                return null;
            }

            @Override
            public float complexity() {
                return 0.5F;
            }
        },
        TEST2(){
            @Override
            public boolean compatible(IEffect effect) {
                return false;
            }

            @Override
            public boolean is(EffectType type) {
                return EffectType.ALCOHOLIC == type;
            }

            @Override
            public IEffect combine(IEffect other) {
                return null;
            }

            @Override
            public float complexity() {
                return 0.15F;
            }
        };

        @Override
        public String localizedName(){
            return this.name().toLowerCase();
        }

        @Override
        public String uuid() {
            return "herbarium." + this.name().toLowerCase();
        }
    }

    public void testCanonicalize()
    throws Exception {
        EffectGraph graph = new EffectGraph();
        for(Effects effect : Effects.values()){
            graph.add(effect);
        }
        graph.optimize();

        Random rand = new Random();

        System.out.println("Potion?: " + Boolean.toString(graph.isMajorly(EffectType.POTION)));
        System.out.println("Poison?: " + Boolean.toString(graph.isMajorly(EffectType.POISON)));
        System.out.println("Alcoholic?: " + Boolean.toString(graph.isMajorly(EffectType.ALCOHOLIC)));

        System.out.println("Majority: " + graph.majority().name());

        for(EffectType type : EffectType.values()){
            System.out.println(type.name() + " Count: " + graph.count(type));
        }

        System.out.println("Apprentice: " + graph.canBrew(rand, BrewmanLevel.APPRENTICE));
        System.out.println("Adept: " + graph.canBrew(rand, BrewmanLevel.ADEPT));
        System.out.println("Expert: " + graph.canBrew(rand, BrewmanLevel.EXPERT));
        System.out.println("Master: " + graph.canBrew(rand, BrewmanLevel.MASTER));
    }
}