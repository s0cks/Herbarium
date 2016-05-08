package herbarium.common.core.brew.effects.effect;

import herbarium.api.brew.EnumBrewType;
import herbarium.api.brew.effects.IEffect;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

import java.util.concurrent.TimeUnit;

public enum SpiritEffects
implements IEffect {
    HASTE(TimeUnit.MINUTES.toMillis(10)){
        @Override
        public float breakSpeed(EntityPlayer player, float originalSpeed) {
            return originalSpeed * 2.0F;
        }
    };

    private final long duration;

    SpiritEffects(long duration){
        this.duration = duration;
    }

    @Override
    public String uuid() {
        return "herbarium.effects.brew." + this.name().toLowerCase();
    }

    @Override
    public EnumBrewType type() {
        return EnumBrewType.SPIRIT;
    }

    @Override public void onTick(EntityPlayer player) {}
    @Override public void onDrink(EntityPlayer player) {}
    @Override public void onTimeout(EntityPlayer player) {}
    @Override public void onJump(EntityPlayer player) {}
    @Override public void onActiveBlock(EntityPlayer player, BlockPos pos, IBlockState state){}
    @Override public void onTargeted(EntityPlayer player, EntityLivingBase targeter){}
    @Override public float breakSpeed(EntityPlayer player, float originalSpeed){ return originalSpeed; }

    @Override
    public long duration() {
        return this.duration;
    }
}