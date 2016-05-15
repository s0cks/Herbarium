package herbarium.common.tiles;

import herbarium.api.brew.piping.IBrewStack;
import herbarium.common.core.brew.piping.BrewStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public final class TileEntityBrewBarrel
extends TileEntity
implements ITickable {
  private IBrewStack stack;

  @Override
  public void readFromNBT(NBTTagCompound compound) {
    super.readFromNBT(compound);

    if (compound.hasKey("Stack")) {
      this.stack = BrewStack.loadFromNBT(compound.getCompoundTag("Stack"));
    }
  }

  @Override
  public void writeToNBT(NBTTagCompound compound) {
    super.writeToNBT(compound);

    if (this.stack != null) {
      NBTTagCompound stackCompound = new NBTTagCompound();
      this.stack.writeToNBT(stackCompound);
      compound.setTag("Stack", stackCompound);
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

  @Override
  public void update() {

  }
}