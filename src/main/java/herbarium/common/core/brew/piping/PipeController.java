package herbarium.common.core.brew.piping;

import herbarium.api.brew.piping.IPipeConnector;
import herbarium.api.brew.piping.PipeConnection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public final class PipeController
    implements IPipeConnector {
  private final TileEntity owner;
  private final boolean source;
  private final Set<PipeConnection> connections = new TreeSet<>(new PipeDirectionComparator());

  public PipeController(TileEntity owner, boolean source) {
    this.owner = owner;
    this.source = source;
  }

  public void writeToNBT(NBTTagCompound comp) {
    NBTTagList list = new NBTTagList();
    for (PipeConnection pipe : this) {
      NBTTagCompound c = new NBTTagCompound();
      pipe.writeToNBT(c);
      list.appendTag(c);
    }
    comp.setTag("Connections", list);
  }

  public void readFromNBT(NBTTagCompound comp) {
    if (!comp.hasKey("Connections")) return;
    NBTTagList list = comp.getTagList("Connections", 10);
    this.connections.clear();
    for (int i = 0; i < list.tagCount(); i++) {
      NBTTagCompound c = list.getCompoundTagAt(i);
      this.connections.add(PipeConnection.fromNBT(c));
    }
  }

  @Override
  public BlockPos position() {
    return this.owner.getPos();
  }

  @Override
  public void add(BlockPos pos) {
    if (hasConnectionTo(pos)) return;
    this.connections.add(this.create(pos));
  }

  @Override
  public void remove(BlockPos pos) {
    if (this.connections.isEmpty()) return;
    if (!hasConnectionTo(pos)) return;

    for (PipeConnection conn : this) {
      if (conn.from.equals(pos)) {
        this.connections.remove(conn);
      }
    }
  }

  @Override
  public boolean hasConnectionTo(BlockPos pos) {
    if (this.connections.isEmpty()) return false;
    for (PipeConnection conn : this) {
      if (conn.from.equals(pos)) {
        return true;
      }
    }

    return false;
  }

  @Override
  public PipeConnection create(BlockPos other) {
    return new PipeConnection(other, this.position(), this.source);
  }

  @Override
  public Iterator<PipeConnection> iterator() {
    return this.connections.iterator();
  }

  private static final class PipeDirectionComparator
      implements Comparator<PipeConnection> {
    @Override
    public int compare(PipeConnection pipeConnection, PipeConnection t1) {
      return pipeConnection.direction.compareTo(t1.direction);
    }
  }
}