package herbarium.common;

import herbarium.client.render.RenderEffectTray;
import net.minecraft.client.Minecraft;

public class CommonProxy{
    public void registerRenders(){}
    public void registerColors(){}
    public Minecraft getClient(){ return null; }
    public RenderEffectTray renderEffectTray(){ return null; }
    public void registerRenderEffectTray(){}
}