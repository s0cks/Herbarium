package herbarium.common.core;

import herbarium.common.Herbarium;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.GameData;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Ruin {
  private static final FMLControlledNamespacedRegistry<Block> registry = GameData.getBlockRegistry();

  private final short[][][] blocks;
  private final byte[][][] metadata;
  private final List<TileEntity> tiles = new LinkedList<>();
  private final int width;
  private final int height;
  private final int length;

  protected Ruin(int width, int height, int length) {
    this.blocks = new short[width][height][length];
    this.metadata = new byte[width][height][length];

    this.width = width;
    this.height = height;
    this.length = length;
  }

  public static Ruin create(ResourceLocation loc) {
    try (InputStream in = Herbarium.proxy.getClient()
                                         .getResourceManager()
                                         .getResource(loc)
                                         .getInputStream()) {
      return loadFromNBT(CompressedStreamTools.readCompressed(in));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static Ruin loadFromNBT(NBTTagCompound compound) {
    System.out.println(compound.hasKey("Schematic"));
    byte[] localBlocks = compound.getByteArray("Blocks");
    byte[] localMetadata = compound.getByteArray("Data");

    boolean extra = false;
    byte[] extraBlocks = null;
    byte[] extraBlocksNibble;

    if (compound.hasKey("AddBlocks")) {
      extra = true;
      extraBlocksNibble = compound.getByteArray("AddBlocks");
      extraBlocks = new byte[extraBlocksNibble.length * 2];

      for (int i = 0; i < extraBlocksNibble.length; i++) {
        extraBlocks[i * 2] = ((byte) ((extraBlocksNibble[i] >> 4) & 0xFF));
        extraBlocks[i * 2 + 1] = ((byte) (extraBlocksNibble[i] & 0xFF));
      }
    } else if (compound.hasKey("Add")) {
      extra = true;
      extraBlocks = compound.getByteArray("Add");
    }

    short width = compound.getShort("Width");
    short height = compound.getShort("Height");
    short length = compound.getShort("Length");

    Map<Short, Short> oldToNew = new HashMap<>();
    if (compound.hasKey("SchematicaMapping")) {
      NBTTagCompound mapping = compound.getCompoundTag("SchematicaMapping");
      Set<String> names = mapping.getKeySet();
      for (String name : names) {
        oldToNew.put(mapping.getShort(name), ((short) registry.getId(new ResourceLocation(name))));
      }
    }

    Ruin ruin = new Ruin(width, height, length);

    Short oldId;
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        for (int z = 0; z < length; z++) {
          int index = x + (y * length + z) * width;

          int blockID = (localBlocks[index] & 0xFF) | (extra
                                                       ? ((extraBlocks[index] & 0xFF) << 8)
                                                       : 0);
          int meta = (localMetadata[index] & 0xFF);

          if ((oldId = oldToNew.get(((short) blockID))) != null) {
            blockID = oldId;
          }

          try {
            if (!ruin.setBlockState(new BlockPos(x, y, z), Block.getBlockById(blockID)
                                                                .getStateFromMeta(meta)))
              System.out.println("Cant add block");
          } catch (Exception e) {
            e.printStackTrace(System.err);
            // Fallthrough
          }
        }
      }
    }

    NBTTagList tileEntitiesList = compound.getTagList("TileEntities", Constants.NBT.TAG_COMPOUND);
    for (int i = 0; i < tileEntitiesList.tagCount(); i++) {
      try {
        TileEntity tile = TileEntity.createTileEntity(null, tileEntitiesList.getCompoundTagAt(i));
        if (tile != null) ruin.setTileEntity(tile.getPos(), tile);
      } catch (Exception e) {
        // Fallthrough
      }
    }

    return ruin;
  }

  public void spawn(World world, BlockPos pos) {
    for (int x = 0; x < this.width; x++) {
      for (int y = 0; y < this.height; y++) {
        for (int z = 0; z < this.length; z++) {
          BlockPos coord = new BlockPos(x, y, z);
          BlockPos position = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
          world.setBlockState(position, this.getBlockState(coord));

          TileEntity tile = this.getTileEntity(coord);
          if (tile != null) world.setTileEntity(position, tile);
        }
      }
    }
  }

  public IBlockState getBlockState(BlockPos pos) {
    if (!this.valid(pos)) return Blocks.AIR.getDefaultState();

    int x = pos.getX();
    int y = pos.getY();
    int z = pos.getZ();

    return Block.getBlockById(this.blocks[x][y][z])
                .getStateFromMeta(this.metadata[x][y][z]);
  }

  public boolean setBlockState(BlockPos pos, IBlockState state) {
    if (!this.valid(pos)) return false;

    Block block = state.getBlock();
    int id = Block.getIdFromBlock(block);

    if (id == -1) return false;

    int x = pos.getX();
    int y = pos.getY();
    int z = pos.getZ();

    this.blocks[x][y][z] = ((short) id);
    this.metadata[x][y][z] = ((byte) block.getMetaFromState(state));
    return true;
  }

  public TileEntity getTileEntity(BlockPos pos) {
    for (TileEntity tile : this.tiles) {
      if (tile.getPos()
              .equals(pos)) {
        return tile;
      }
    }

    return null;
  }

  public void setTileEntity(BlockPos pos, TileEntity tile) {
    if (!this.valid(pos)) return;

    Iterator<TileEntity> iterator = this.tiles.iterator();
    while (iterator.hasNext()) {
      TileEntity t = iterator.next();
      if (t.getPos()
           .equals(pos)) {
        iterator.remove();
      }
    }

    this.tiles.add(tile);
  }

  private boolean valid(BlockPos pos) {
    int x = pos.getX();
    int y = pos.getY();
    int z = pos.getZ();
    return !(x < 0 || y < 0 || z < 0 || x >= this.width || y >= this.height || z >= this.length);
  }
}