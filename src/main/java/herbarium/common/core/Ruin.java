package herbarium.common.core;

import herbarium.api.ruins.IRuin;
import herbarium.api.ruins.IRuinContext;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public final class Ruin
implements IRuin,
           IRuinContext {
  private final String name;
  private final String[][] template;
  private final Map<String, String> context = new HashMap<>();

  public Ruin(String name, String[][] template) {
    this.name = name;
    this.template = template;
  }

  @Override
  public String uuid() {
    return "herbarium.ruin." + this.name.toLowerCase();
  }

  @Override
  public ResourceLocation resource() {
    return new ResourceLocation("herbarium", "ruins/" + this.name.toLowerCase() + ".json");
  }

  @Override
  public String[][] template() {
    return this.template;
  }

  @Override
  public IRuinContext context() {
    return this;
  }

  @Override
  public Block map(char sym) {
    return Block.getBlockFromName(this.context.get(String.valueOf(sym)));
  }

  @Override
  public void define(char sym, Block block) {
    this.context.put(String.valueOf(sym), block.getRegistryName()
                                               .getResourceDomain() + ":" + block.getRegistryName()
                                                                                 .getResourcePath());
  }
}