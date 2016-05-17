package herbarium.common;

import herbarium.common.blocks.BlockDebug;
import herbarium.common.blocks.BlockJournal;
import herbarium.common.blocks.brewing.BlockBarrel;
import herbarium.common.blocks.brewing.BlockCoalescer;
import herbarium.common.blocks.brewing.BlockCoil;
import herbarium.common.blocks.brewing.BlockCrucible;
import herbarium.common.blocks.brewing.BlockFermenter;
import herbarium.common.blocks.brewing.BlockFlume;
import herbarium.common.blocks.brewing.BlockMortar;
import herbarium.common.blocks.brewing.BlockPipe;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class HerbariumBlocks {
  public static final Block blockCrucible = new BlockCrucible()
                                            .setCreativeTab(Herbarium.tab)
                                            .setUnlocalizedName("crucible")
                                            .setRegistryName(new ResourceLocation("herbarium", "crucible"));
  public static final Block blockCoil = new BlockCoil()
                                        .setCreativeTab(Herbarium.tab)
                                        .setUnlocalizedName("coil")
                                        .setRegistryName(new ResourceLocation("herbarium", "coil"));
  public static final Block blockFlume = new BlockFlume()
                                         .setCreativeTab(Herbarium.tab)
                                         .setUnlocalizedName("flume")
                                         .setRegistryName(new ResourceLocation("herbarium", "flume"));
  public static final Block blockPipe = new BlockPipe()
                                        .setCreativeTab(Herbarium.tab)
                                        .setUnlocalizedName("pipe")
                                        .setRegistryName(new ResourceLocation("herbarium", "pipe"));
  public static final Block blockMortar = new BlockMortar()
                                          .setCreativeTab(Herbarium.tab)
                                          .setUnlocalizedName("mortar")
                                          .setRegistryName(new ResourceLocation("herbarium", "mortar"));
  public static final Block blockBarrel = new BlockBarrel()
                                          .setCreativeTab(Herbarium.tab)
                                          .setUnlocalizedName("barrel")
                                          .setRegistryName(new ResourceLocation("herbarium", "barrel"));
  public static final Block blockJournal = new BlockJournal()
                                           .setCreativeTab(Herbarium.tab)
                                           .setUnlocalizedName("journal")
                                           .setRegistryName(new ResourceLocation("herbarium", "journal"));
  public static final Block blockDebug = new BlockDebug()
                                         .setCreativeTab(Herbarium.tab)
                                         .setUnlocalizedName("debug")
                                         .setRegistryName(new ResourceLocation("herbarium", "debug"));
  public static final Block blockCoalescer = new BlockCoalescer()
                                             .setCreativeTab(Herbarium.tab)
                                             .setUnlocalizedName("coalescer")
                                             .setRegistryName(new ResourceLocation("herbarium", "coalescer"));
  public static final Block blockFermenter = new BlockFermenter()
                                             .setCreativeTab(Herbarium.tab)
                                             .setUnlocalizedName("fermenter")
                                             .setRegistryName(new ResourceLocation("herbarium", "fermenter"));

  public static void init() {
    GameRegistry.register(blockCrucible);
    GameRegistry.register(blockCoil);
    GameRegistry.register(blockFlume);
    GameRegistry.register(blockPipe);
    GameRegistry.register(blockMortar);
    GameRegistry.register(blockBarrel);
    GameRegistry.register(blockJournal);
    GameRegistry.register(blockDebug);
    GameRegistry.register(blockCoalescer);
    GameRegistry.register(blockFermenter);
  }
}