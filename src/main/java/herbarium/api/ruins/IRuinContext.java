package herbarium.api.ruins;

import net.minecraft.block.Block;

public interface IRuinContext{
    public Block map(char sym);
    public void define(char sym, Block block);
}