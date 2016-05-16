package herbarium.common.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;

public final class TileEntityRuinSelector
extends TileEntity{
  private static final AxisAlignedBB MIN_AABB = new AxisAlignedBB(0, 0, 0, 1, 1, 1);

  private AxisAlignedBB box = MIN_AABB;

  public TileEntityRuinSelector expand(){
    this.box = new AxisAlignedBB(this.box.minX - 1.0, 0.0, this.box.minZ - 1.0,  this.box.maxX + 1.0, this.box.maxY + 1.0, this.box.maxZ + 1.0);
    return this;
  }

  public TileEntityRuinSelector contract(){
    if(this.box.maxX == 1){
      return this;
    }

    if(this.box.maxY == 1){
      return this;
    }

    if(this.box.maxZ == 1){
      return this;
    }

    this.box = this.box.expandXyz(-1.0);
    return this;
  }

  public AxisAlignedBB getBox(){
    return this.box;
  }

  @Override
  public void readFromNBT(NBTTagCompound compound) {
    super.readFromNBT(compound);

    if(compound.hasKey("Box")){
      NBTTagCompound comp = compound.getCompoundTag("Box");
      this.box = new AxisAlignedBB(comp.getDouble("MinX"), comp.getDouble("MinY"), comp.getDouble("MinZ"), comp.getDouble("MaxX"), comp.getDouble("MaxY"), comp.getDouble("MaxZ"));
    }
  }

  @Override
  public void writeToNBT(NBTTagCompound compound){
    super.writeToNBT(compound);

    NBTTagCompound comp = new NBTTagCompound();
    comp.setDouble("MinX", this.box.minX);
    comp.setDouble("MinY", this.box.minY);
    comp.setDouble("MinZ", this.box.minZ);

    comp.setDouble("MaxX", this.box.maxX);
    comp.setDouble("MaxY", this.box.maxY);
    comp.setDouble("MaxZ", this.box.maxZ);

    compound.setTag("Box", comp);
  }
}