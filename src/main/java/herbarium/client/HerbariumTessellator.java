package herbarium.client;

import net.minecraft.client.renderer.Tessellator;

public final class HerbariumTessellator
extends Tessellator {
  private static HerbariumTessellator instance = null;

  private HerbariumTessellator() {
    super(0x200000);
  }

  public static HerbariumTessellator instance() {
    return (instance == null
            ? (instance = new HerbariumTessellator())
            : instance);
  }
}