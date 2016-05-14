package herbarium.common.core;

import herbarium.client.render.RenderEffectTray;
import herbarium.common.Herbarium;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public final class KeyHandler {
  @SubscribeEvent
  public void onKeyPress(InputEvent.KeyInputEvent e) {
    if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
      RenderEffectTray tray = Herbarium.proxy.renderEffectTray();
      if (tray != null && !tray.showing()) {
        tray.show();
      } else if (tray != null) {
        tray.hide();
      }
    }
  }
}