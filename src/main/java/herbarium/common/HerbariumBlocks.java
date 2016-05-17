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
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class HerbariumBlocks {
  public static final Block blockCrucible = new BlockCrucible()
                                            .setCreativeTab(Herbarium.tab)
                                            .setUnlocalizedName("herba_crucible");
  public static final Block blockCoil = new BlockCoil()
                                        .setCreativeTab(Herbarium.tab)
                                        .setUnlocalizedName("herba_coil");
  public static final Block blockFlume = new BlockFlume()
                                         .setCreativeTab(Herbarium.tab)
                                         .setUnlocalizedName("herba_flume");
  public static final Block blockPipe = new BlockPipe()
                                        .setCreativeTab(Herbarium.tab)
                                        .setUnlocalizedName("herba_pipe");
  public static final Block blockMortar = new BlockMortar()
                                          .setCreativeTab(Herbarium.tab)
                                          .setUnlocalizedName("herba_mortar");
  public static final Block blockBarrel = new BlockBarrel()
                                          .setCreativeTab(Herbarium.tab)
                                          .setUnlocalizedName("herba_barrel");
  public static final Block blockJournal = new BlockJournal()
                                           .setCreativeTab(Herbarium.tab)
                                           .setUnlocalizedName("herba_journal");
  public static final Block blockDebug = new BlockDebug()
                                         .setCreativeTab(Herbarium.tab)
                                         .setUnlocalizedName("herba_debug");
  public static final Block blockCoalescer = new BlockCoalescer()
                                             .setCreativeTab(Herbarium.tab)
                                             .setUnlocalizedName("hebra_coalescer");
  public static final Block blockFermenter = new BlockFermenter()
                                             .setCreativeTab(Herbarium.tab)
                                             .setUnlocalizedName("herba_fermenter");

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