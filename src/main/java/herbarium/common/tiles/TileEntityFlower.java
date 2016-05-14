package herbarium.common.tiles;

import herbarium.api.botany.IFlower;
import herbarium.common.core.botany.Flower;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public final class TileEntityFlower
extends TileEntity{
  protected IFlower flower;

  public TileEntityFlower(IFlower flower){
    this.flower = flower;
  }

  public IFlower flower(){
    return this.flower;
  }

  @Override
  public void writeToNBT(NBTTagCompound compound) {
    super.writeToNBT(compound);
    NBTTagCompound flowerComp = new NBTTagCompound();
    this.flower.writeToNBT(flowerComp);
    compound.setTag("Flower", flowerComp);
  }

  @Override
  public void readFromNBT(NBTTagCompound compound) {
    super.readFromNBT(compound);
    if(compound.hasKey("Flower")){
      this.flower = new Flower(compound.getCompoundTag("Flower"));
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