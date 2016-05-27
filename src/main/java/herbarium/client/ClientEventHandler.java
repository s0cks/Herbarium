package herbarium.client;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class ClientEventHandler{
  private static ClientEventHandler instance;

  public static ClientEventHandler instance(){
    return (instance == null ? (instance = new ClientEventHandler()) : instance);
  }

  private TextureAtlasSprite pipe;

  @SubscribeEvent
  public void onTextureStitch(TextureStitchEvent e){
    this.pipe = e.getMap().registerSprite(new ResourceLocation("herbarium", "textures/models/pipe.png"));
  }

  public TextureAtlasSprite pipeTexture(){
    return this.pipe;
  }
}