package herbarium.common.core;

import herbarium.api.ruins.IRuin;
import herbarium.api.ruins.IRuinContext;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public final class Ruin
implements IRuin {
    private final String name;

    public Ruin(String name){
        this.name = name;
    }

    @Override
    public String uuid() {
        return "herbarium.ruin." + this.name.toLowerCase();
    }

    @Override
    public ResourceLocation template() {
        return new ResourceLocation("herbarium", "ruins/" + this.name.toLowerCase() + ".json");
    }

    @Override
    public IRuinContext context() {
        return new RuinContext();
    }

    private static final class RuinContext
    implements IRuinContext{
        private final Map<Character, Block> map = new HashMap<>();

        public RuinContext(){
            this.define('X', Blocks.stone);
            this.define('R', Blocks.redstone_block);
            this.define('W', Blocks.planks);
        }

        @Override
        public Block map(char sym) {
            return this.map.get(sym);
        }

        @Override
        public void define(char sym, Block block) {
            this.map.put(sym, block);
        }
    }
}