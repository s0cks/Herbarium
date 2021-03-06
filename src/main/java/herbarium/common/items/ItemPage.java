package herbarium.common.items;

import herbarium.api.HerbariumApi;
import herbarium.api.commentarium.pages.IPage;
import herbarium.common.core.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.util.List;

public final class ItemPage
extends Item {
  private static final String PAGE_TAG = "Page";

  public static void setPage(ItemStack stack, IPage page) {
    NBTTagCompound comp = NBTHelper.getCompound(stack);
    if (comp == null) comp = new NBTTagCompound();
    comp.setString(PAGE_TAG, page.uuid());
    if (!stack.hasTagCompound()) stack.setTagCompound(comp);
  }

  public static IPage getPage(ItemStack stack) {
    NBTTagCompound comp = NBTHelper.getCompound(stack);
    if (comp == null) return null;
    return HerbariumApi.PAGE_MANAGER.get(comp.getString(PAGE_TAG));
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
    IPage page = getPage(itemStackIn);
    if (!playerIn.isSneaking()) {
      if (page != null & !worldIn.isRemote) {
        HerbariumApi.PAGE_TRACKER.learn(playerIn, page);
        playerIn.inventory.getCurrentItem().stackSize--;
        double x = playerIn.posX + worldIn.rand.nextInt(150);
        double z = playerIn.posZ + worldIn.rand.nextInt(150);
        BlockPos pos = worldIn.getTopSolidOrLiquidBlock(new BlockPos(x, 1, z));
        playerIn.addChatComponentMessage(new TextComponentString("Spawned Ruin @" + pos));
      }
    } else {
      if (page != null) {
        //TODO: Fix sounds?
        // page.flip();
        // worldIn.playSound(playerIn, playerIn.getPosition(), HerbariumSounds.pageFlip, SoundCategory.AMBIENT, 1.0F, 1.0F);
      }
    }
    return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
  }

  @Override
  public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
    IPage page = getPage(stack);
    if (page == null) return;
    tooltip.add("Page " + page.title());
  }
}