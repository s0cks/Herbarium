package herbarium.common.core.brew.effects.effect;

import herbarium.api.brew.EnumBrewType;
import herbarium.api.brew.effects.IEffect;
import herbarium.common.Herbarium;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.concurrent.TimeUnit;

public enum VenomEffects
implements IEffect {
    LEVITATION(TimeUnit.MINUTES.toMillis(3)), //Lifts entities into air
    IGNITION(TimeUnit.MINUTES.toMillis(3)), //Sets entities on fire, gives them swiftness
    BLINDNESS(TimeUnit.MINUTES.toMillis(3)), //Blinds entities, makes them prone to crit
    CHILLED(TimeUnit.MINUTES.toMillis(3)), //Gives DoT, slows entities down,
    POISON(TimeUnit.MINUTES.toMillis(3)), //Poisons players, induces hunger
    ELECTROLYZED(TimeUnit.MINUTES.toMillis(3)), //Slows the players attack speed, causes them to randomly stop for few seconds
    MARKED(TimeUnit.MINUTES.toMillis(3)), //Marks the entity longer than PROWLER, decreases armour
    HEAVY(TimeUnit.MINUTES.toMillis(3)), //Players fall more quickly, increased fall damage, higher resistance
    HURLING(TimeUnit.MINUTES.toMillis(3)), //Entities are hurled around
    SCARED(TimeUnit.MINUTES.toMillis(3)), //Backs away from caster, decreased damage towards them
    DEATH_CLOCK(TimeUnit.MINUTES.toMillis(3)); //Entity will be damaged after time


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
        return EnumBrewType.VENOM;
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