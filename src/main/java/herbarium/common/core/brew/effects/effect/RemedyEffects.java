package herbarium.common.core.brew.effects.effect;

import herbarium.api.brew.EnumBrewType;
import herbarium.api.brew.effects.IEffect;
import herbarium.common.Herbarium;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

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
    }, //Fertilizing action is 2x strong
    HASTE(TimeUnit.MINUTES.toMillis(10)){ //More block breaking,
        @Override
        public float breakSpeed(EntityPlayer player, float originalSpeed) {
            return originalSpeed * 2.0F;
        }
    },
    MENACING(TimeUnit.MINUTES.toMillis(3)){
        @Override
        public void onTargeted(EntityPlayer player, EntityLivingBase targeter) {
            if((targeter instanceof EntityMob) && Herbarium.random.nextBoolean()){
                ((EntityMob) targeter).setAttackTarget(null);
            }
        }
    }, //Mobs ignore you
    ROBUST(TimeUnit.MINUTES.toMillis(3)), //Inflict bleeding (DoT), more knockback, more crits
    WATER_WALKING(TimeUnit.MINUTES.toMillis(3)){
        @Override
        public void onTick(EntityPlayer player) {
            BlockPos below = player.getPosition().down();
            World world = player.getEntityWorld();
            if(world.getBlockState(below).getBlock().getMaterial(world.getBlockState(below)).equals(Material.WATER)){
                if(player.motionY < 0 && player.getEntityBoundingBox().minY < (player.posY - player.getYOffset())){
                    player.motionY = 0;
                    player.fallDistance = 0;
                    player.onGround = true;
                    if(player.isSneaking()){
                        player.motionY -= 0.1F;
                    }
                }
            }
        }
    }, //Walking on water
    FIRE_RESISTANCE(TimeUnit.MINUTES.toMillis(3)), //Fire Resistance, more vulnerable to coldness
    PROWLER_VISION(TimeUnit.MINUTES.toMillis(10)), //See nearby mobs with an outline, mobs notice you more early
    SWIFT_HANDS(TimeUnit.MINUTES.toMillis(10)), //Less cooldown on attacks, less defense
    UNSEEN(TimeUnit.MINUTES.toMillis(10)), //Invincibility (better than vanilla), no walking sound, highly more vulnerable
    LIGHTWEIGHT(TimeUnit.MINUTES.toMillis(3)), //More resistant to falls, fast speed, more vulnerable to explosions/knockback, doesnt trigger tripwire/plates
    IRON_SKIN(TimeUnit.MINUTES.toMillis(3)); //You get damaged less but you move a tad slower

    private final ResourceLocation icon;
    private final long duration;

    RemedyEffects(long duration) {
        this.duration = duration;
        this.icon = new ResourceLocation("herbarium", "textures/effects/" + this.name().toLowerCase() + ".png");
    }


    @Override
    public String uuid() {
        return "herbarium.effects.remedy." + this.name().toLowerCase();
    }

    @Override
    public EnumBrewType type() {
        return EnumBrewType.REMEDY;
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

    @Override
    public ResourceLocation icon() {
        return this.icon;
    }
}