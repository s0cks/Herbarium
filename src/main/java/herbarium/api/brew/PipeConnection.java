package herbarium.api.brew;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public final class PipeConnection{
    public final BlockPos from, to;
    public final EnumFacing direction;
    public final boolean source;

    public PipeConnection(BlockPos from, BlockPos to, EnumFacing direction, boolean source) {
        this.from = from;
        this.to = to;
        this.direction = direction;
        this.source = source;
    }

    public PipeConnection(BlockPos from, BlockPos to){
        this(from, to, derive(from, to), false);
    }

    public PipeConnection(BlockPos from, BlockPos to, boolean source){
        this(from, to, derive(from, to), source);
    }

    public boolean equals(PipeConnection conn){
        return conn.direction == this.direction &&
                       conn.to.equals(this.to) &&
                       conn.from.equals(this.from);
    }

    public TileEntity getSource(World world){
        return this.source ?
                       world.getTileEntity(this.from) :
                       world.getTileEntity(this.to);
    }

    public void writeToNBT(NBTTagCompound comp){
        comp.setIntArray("From", new int[]{ this.from.getX(), this.from.getY(), this.from.getZ() });
        comp.setIntArray("To", new int[]{ this.to.getX(), this.to.getY(), this.to.getZ() });
        comp.setBoolean("Source", this.source);
        comp.setInteger("Direction", this.direction.ordinal());
    }

    public static EnumFacing derive(BlockPos from, BlockPos to){
        if(to == null || from == null) return null;

        int dX = to.getX() - from.getX();
        int dY = to.getY() - from.getY();
        int dZ = to.getZ() - from.getZ();

        if(Math.abs(dX) == 1 && dY == 0 && dZ == 0){
            return dX == 1 ?
                           EnumFacing.EAST :
                           EnumFacing.WEST;
        } else if(dX == 0 && Math.abs(dY) == 1 && dZ == 0){
            return dY == 1 ?
                           EnumFacing.UP :
                           EnumFacing.DOWN;
        } else if(dX == 0 && dY == 0 && Math.abs(dZ) == 1){
            return dZ == 1 ?
                           EnumFacing.SOUTH :
                           EnumFacing.NORTH;
        }

        return null;
    }

    public static PipeConnection fromNBT(NBTTagCompound comp){
        int[] fromArray = comp.getIntArray("From");
        int[] toArray = comp.getIntArray("To");
        return new PipeConnection(
            new BlockPos(fromArray[0], fromArray[1], fromArray[2]),
            new BlockPos(toArray[0], toArray[1], toArray[2]),
            EnumFacing.values()[comp.getInteger("Direction")],
            comp.getBoolean("Source")
        );
    }
}