package herbarium.common.items;

import herbarium.api.HerbariumApi;
import herbarium.api.brew.BrewmanLevel;
import herbarium.api.commentarium.IPage;
import herbarium.common.core.brew.BrewLevelManager;
import herbarium.common.core.brew.PlayerBrewLevel;
import herbarium.common.core.commentarium.DefaultPages;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.List;

public final class ItemPage
extends Item {
    private static final String PAGE_TAG = "PAGE";

    public static IPage getPage(ItemStack stack){
        if(!stack.hasTagCompound()) return null;
        NBTTagCompound comp = stack.getTagCompound();
        if(!comp.hasKey(PAGE_TAG)) return null;
        return HerbariumApi.PAGE_MANAGER.get(comp.getString(PAGE_TAG));
    }

    public static void setPage(ItemStack stack, IPage page){
        if(stack == null || page == null) return;
        if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setString(PAGE_TAG, page.uuid());
    }

    private IPage page = DefaultPages.CONTENTS;

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        PlayerBrewLevel level = PlayerBrewLevel.get(playerIn);
        level.set(BrewmanLevel.next(level.get()));
        BrewLevelManager.INSTANCE.sync(playerIn);
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
    }
}