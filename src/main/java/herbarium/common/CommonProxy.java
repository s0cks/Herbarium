package herbarium.common;

import herbarium.client.render.RenderEffectTray;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.block.model.ModelManager;

public class CommonProxy {
  public ModelManager modelManager() { return null; }

  public BlockRendererDispatcher dispatcher() { return null; }

  public void registerRenders() {}

  public void registerColors() {}

  public Minecraft getClient() { return null; }

  public RenderEffectTray renderEffectTray() { return null; }

  public void preInit(){}
  public void init() {}
}