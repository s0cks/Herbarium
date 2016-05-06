package herbarium.common.core.brew.effects.effect;

import herbarium.api.brew.effects.IEffect;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

import java.util.concurrent.TimeUnit;

public final class EffectDebug
implements IEffect {
    @Override
    public String uuid() {
        return "herbarium.effect.debug";
    }

    @Override
    public void onTick(EntityPlayer player) {
        player.addChatComponentMessage(new TextComponentString("Test"));
    }

    @Override
    public void onDrink(EntityPlayer player) {
        player.addChatComponentMessage(new TextComponentString("Hello World"));
    }

    @Override
    public void onTimeout(EntityPlayer player) {
        player.addChatComponentMessage(new TextComponentString("Done"));
    }

    @Override
    public long duration() {
        return TimeUnit.SECONDS.toMillis(10);
    }
}