package herbarium.api.brew.effects;

import herbarium.api.brew.EnumBrewType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public interface IEffect{
    public String uuid();
    public EnumBrewType type();
    public void onTick(EntityPlayer player);
    public void onDrink(EntityPlayer player);
    public void onTimeout(EntityPlayer player);
    public void onJump(EntityPlayer player);
    public void onActiveBlock(EntityPlayer player, BlockPos pos, IBlockState b);
    public void onTargeted(EntityPlayer player, EntityLivingBase targeter);
    public float breakSpeed(EntityPlayer player, float originalSpeed);
    public long duration();
}