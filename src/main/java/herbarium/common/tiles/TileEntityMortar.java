package herbarium.common.tiles;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public final class TileEntityMortar
extends TileEntity {
  private ItemStack currentItem;
  private int pasteLevel;

  public void mush() {
    if (this.currentItem == null || this.currentItem.stackSize <= 0 || this.pasteLevel == 64) {
      this.currentItem = null;
      return;
    }

    int amount = this.getWorld().rand.nextInt(this.currentItem.stackSize + 1);
    this.currentItem.stackSize -= amount;
    this.pasteLevel += amount;
    if (this.currentItem.stackSize <= 0 || this.pasteLevel >= 64) this.currentItem = null;
  }

  public float getPasteLevel() {
    return ((float) this.pasteLevel / 64.0F);
  }

  public ItemStack getCurrentItem() {
    return this.currentItem;
  }

  public void setCurrentItem(ItemStack stack) {
    this.pasteLevel = 0;
    this.currentItem = ItemStack.copyItemStack(stack);
  }

  public void update() {
    this.markDirty();
    this.getWorld()
        .notifyBlockUpdate(this.getPos(), this.getWorld()
                                              .getBlockState(this.getPos()), this.getWorld()
                                                                                 .getBlockState(this.getPos()), 3);
  }

  @Override
  public void readFromNBT(NBTTagCompound comp) {
    super.readFromNBT(comp);
    if (comp.hasKey("PasteLevel")) {
      this.pasteLevel = comp.getInteger("PasteLevel");
    }
    if (comp.hasKey("Item")) {
      this.currentItem = ItemStack.loadItemStackFromNBT(comp.getCompoundTag("Item"));
    }
  }

  @Override
  public void writeToNBT(NBTTagCompound comp) {
    super.writeToNBT(comp);
    comp.setInteger("PasteLevel", this.pasteLevel);
    if (this.currentItem != null) {
      NBTTagCompound itemComp = new NBTTagCompound();
      this.currentItem.writeToNBT(itemComp);
      comp.setTag("Item", itemComp);
    }
  }

  @Override
  public Packet<?> getDescriptionPacket() {
    NBTTagCompound tag = new NBTTagCompound();
    this.writeToNBT(tag);
    return new SPacketUpdateTileEntity(this.getPos(), this.getBlockMetadata(), tag);
  }

  @Override
  public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
    this.readFromNBT(pkt.getNbtCompound());
  }
}