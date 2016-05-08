package herbarium.common.core.brew.effects.effect;

import herbarium.api.brew.EnumBrewType;
import herbarium.api.brew.effects.IEffect;
import herbarium.common.Herbarium;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.concurrent.TimeUnit;

public enum VenomEffects
implements IEffect {
    HOTFOOT(TimeUnit.SECONDS.toMillis(30)){
        @Override
        public void onTick(EntityPlayer player) {
            World world = player.getEntityWorld();
            BlockPos pos = getBehind(player);
            if(world.isAirBlock(pos) && Herbarium.random.nextBoolean() && player.onGround){
                world.setBlockState(pos, Blocks.FIRE.getDefaultState());
            }
        }

        private BlockPos getBehind(EntityPlayer player){
            switch(player.getHorizontalFacing()){
                case WEST: return player.getPosition().east();
                case EAST: return player.getPosition().west();
                case SOUTH: return player.getPosition().north();
                case NORTH: return player.getPosition().south();
                default: return player.getPosition();
            }
        }
    };

    private final long duration;

    VenomEffects(long duration) {
        this.duration = duration;
    }

    @Override
    public String uuid() {
        return "herbarium.effects.venom." + this.name().toLowerCase();
    }

    @Override
    public EnumBrewType type() {
        return EnumBrewType.POISON;
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