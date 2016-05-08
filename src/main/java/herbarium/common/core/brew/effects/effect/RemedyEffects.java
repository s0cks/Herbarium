package herbarium.common.core.brew.effects.effect;

import herbarium.api.brew.EnumBrewType;
import herbarium.api.brew.effects.IEffect;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import java.util.concurrent.TimeUnit;

public enum RemedyEffects
implements IEffect{
    GREEN_THUMB(TimeUnit.SECONDS.toMillis(10)){
        @Override
        public void onActiveBlock(EntityPlayer player, BlockPos pos, IBlockState state) {
            player.addChatComponentMessage(new TextComponentString("Checking for crops"));
            if (state.getBlock() instanceof BlockCrops) {
                BlockCrops crops = ((BlockCrops) state.getBlock());
                player.addChatComponentMessage(new TextComponentString("Checking if can grow"));
                if(crops.canGrow(player.getEntityWorld(), pos, state, !player.getEntityWorld().isRemote)){
                    player.addChatComponentMessage(new TextComponentString("Growing"));
                    crops.grow(player.getEntityWorld(), pos, state);
                }
            }
        }
    },
    PROWLER_VISION(TimeUnit.MINUTES.toMillis(3)){
    };

    private final long duration;

    RemedyEffects(long duration) {
        this.duration = duration;
    }


    @Override
    public String uuid() {
        return "herbarium.effects.remedy." + this.name().toLowerCase();
    }

    @Override
    public EnumBrewType type() {
        return EnumBrewType.POTION;
    }

    @Override public void onTick(EntityPlayer player) {}
    @Override public void onDrink(EntityPlayer player) {}
    @Override public void onTimeout(EntityPlayer player) {}
    @Override public void onJump(EntityPlayer player) {}
    @Override public void onActiveBlock(EntityPlayer player, BlockPos pos, IBlockState state){}

    @Override
    public long duration() {
        return this.duration;
    }
}