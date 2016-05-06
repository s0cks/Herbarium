package herbarium.common.items;

import herbarium.api.HerbariumApi;
import herbarium.api.brew.IBrew;
import herbarium.api.brew.effects.IEffect;
import herbarium.common.core.NBTHelper;
import herbarium.common.core.brew.Brew;
import herbarium.common.core.brew.effects.effect.EffectDebug;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.List;

public final class ItemBrew
extends Item {
    private static final String BREW_TAG = "Brew";

    public static void setBrew(ItemStack stack, IBrew brew){
        NBTTagCompound comp = NBTHelper.getCompound(stack);
        if(comp == null) comp = new NBTTagCompound();
        NBTTagCompound brewComp = new NBTTagCompound();
        brew.writeToNBT(brewComp);
        comp.setTag(BREW_TAG, brewComp);
        if(!stack.hasTagCompound()) stack.setTagCompound(comp);
    }

    public static IBrew getBrew(ItemStack stack){
        NBTTagCompound comp = NBTHelper.getCompound(stack);
        if(comp == null) return null;
        if(!comp.hasKey(BREW_TAG)) return null;
        NBTTagCompound brewComp = comp.getCompoundTag(BREW_TAG);
        IBrew brew = new Brew();
        brew.readFromNBT(brewComp);
        return brew;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        IBrew brew = getBrew(itemStackIn);
        if(brew != null && !worldIn.isRemote){
            HerbariumApi.EFFECT_TRACKER.setEffects(playerIn, brew.effects());
            HerbariumApi.EFFECT_TRACKER.syncEffects(playerIn);
        } else{
            Brew b = new Brew();
            b.effects.add(new EffectDebug());
            setBrew(itemStackIn, b);
        }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        IBrew brew = getBrew(stack);
        if(brew != null){
            for(IEffect effect : brew.effects()){
                tooltip.add(effect.uuid());
            }
        }
    }
}