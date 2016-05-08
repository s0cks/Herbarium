package herbarium.common.core.brew.effects.effect;

import herbarium.api.brew.EnumBrewType;
import herbarium.api.brew.effects.IEffect;
import herbarium.common.Herbarium;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.concurrent.TimeUnit;

public enum SpiritEffects
implements IEffect {
    ////T1

    ///Combined Remedy Effects
    //PROWLER + HASTE
    LUCKY(TimeUnit.MINUTES.toMillis(3)), //See gems through walls
    //ROBUST + MENACING
    TERRIFYING(TimeUnit.MINUTES.toMillis(3)){
        @Override
        public void onTargeted(EntityPlayer player, EntityLivingBase targeter) {
            if(targeter instanceof EntityMob){
                ((EntityMob) targeter).setAttackTarget(null);
            }
        }
    }, //Mobs run from you (Like creeper from cats)
    //FIRE_RESISTANCE + WATER_WALKING
    LAVA_WALKING(TimeUnit.MINUTES.toMillis(3)), //You can hot walk on hot liquids
    //IRON_SKIN + FIRE_RESISTANCE
    BLAZING_AURA(TimeUnit.MINUTES.toMillis(3)), //Entities that inflict damage to you are set aflame
    //ROBUST + UNSEEN
    STEALTHY(TimeUnit.MINUTES.toMillis(3)), //Invincible, slightly less vulernability, more damage when sneaking

    ///Combined Venom Effects
    //MARKED + SCARED
    PARALYZED(TimeUnit.MINUTES.toMillis(3)), //You are unable to move
    //POISON + IGNITION
    DECAY(TimeUnit.MINUTES.toMillis(3)), //Inflicts wither damage
    //HEAVY + MARKED
    GRAVITY(TimeUnit.MINUTES.toMillis(3)), //Player inflicts meteors on themselves

    ///Combined Venom + Remedy Effects
    //ELECTROLYZED + IRON_SKIN
    LIGHTNING_ROD(TimeUnit.MINUTES.toMillis(3)), //You are more prone to lightning strikes, you move slower
    //HEAVY + ROBUST
    EARTHQUAKE(TimeUnit.MINUTES.toMillis(3)), //Produces a shockwave that gives damage/knockback/nausea to nearby entities
    //LEVITATION + SWIFT_ARMS
    AIR_PADDLING(TimeUnit.MINUTES.toMillis(3)), //Levitation effect, but you can slightly move
    //LIGHTWEIGHT + IGNITION
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
    }, //Leaves trail of fire
    //LIGHTWEIGHT + HURLING
    TORNADO(TimeUnit.SECONDS.toMillis(30)), //Players are thrown in the air, nausea, blindness



    ////T2

    ///Combined Remedy Effects
    //PROWLER + HASTE
    DWARF_EYES(TimeUnit.MINUTES.toMillis(3)), //See ores through walls
    //SWIFT_HANDS + STEALTHY
    DISARM(TimeUnit.MINUTES.toMillis(3)), //Chance to disarm enemy

    ///Combined Venom Effects
    //CHILLED + PARALYZED
    FROZEN(TimeUnit.MINUTES.toMillis(3)), //After given player will start to freeze, DoT and no moving, weakness

    ///Combined Remedy + Venom Effects
    //IRON_SKIN + ELECTROLYZED
    METALLIC(TimeUnit.MINUTES.toMillis(3)), //You are more prone to lightning strikes, you move slower
    //LIGHTWEIGHT + AIR_PADDLING
    GLIDING(TimeUnit.MINUTES.toMillis(3)), //Elytra effect, slowed down

    //Drunk Side Effects
    FLIPPED_VISION(TimeUnit.MINUTES.toMillis(3)), //Flips your vision
    VOMIT(TimeUnit.MINUTES.toMillis(3)), //Lose a chunk of your hunger
    ORIENTATION_LOSS(TimeUnit.MINUTES.toMillis(3)), //You move slower
    PUZZLED(TimeUnit.MINUTES.toMillis(3)), //Damage dealt and damage received is lowered
    CHARISMATIC(TimeUnit.MINUTES.toMillis(10)), //Better deals with villagers
    NAUSEA(TimeUnit.MINUTES.toMillis(3)); //The world rotates

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
    @Override public void onExplodeGas(EntityPlayer player, BlockPos pos){}
    @Override public void onExplodeLiquid(EntityPlayer player, BlockPos pos){}
    @Override public float breakSpeed(EntityPlayer player, float originalSpeed){ return originalSpeed; }

    @Override
    public long duration() {
        return this.duration;
    }
}