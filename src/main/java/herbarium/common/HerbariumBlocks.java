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
import herbarium.common.blocks.flowers.BlockCaveFlower;
import herbarium.common.blocks.flowers.BlockHerbariumFlower;
import herbarium.common.blocks.flowers.BlockNetherFlower;
import herbarium.common.blocks.flowers.BlockWaterFlower;
import herbarium.common.core.botany.Flowers;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class HerbariumBlocks {
  public static final Block blockCrucible = new BlockCrucible()
                                            .setCreativeTab(Herbarium.tab)
                                            .setUnlocalizedName("crucible");
  public static final Block blockCoil = new BlockCoil()
                                        .setCreativeTab(Herbarium.tab)
                                        .setUnlocalizedName("coil");
  public static final Block blockFlume = new BlockFlume()
                                         .setCreativeTab(Herbarium.tab)
                                         .setUnlocalizedName("flume");
  public static final Block blockPipe = new BlockPipe()
                                        .setCreativeTab(Herbarium.tab)
                                        .setUnlocalizedName("pipe");
  public static final Block blockMortar = new BlockMortar()
                                          .setCreativeTab(Herbarium.tab)
                                          .setUnlocalizedName("mortar");
  public static final Block blockBarrel = new BlockBarrel()
                                          .setCreativeTab(Herbarium.tab)
                                          .setUnlocalizedName("barrel");
  public static final Block blockJournal = new BlockJournal()
                                           .setCreativeTab(Herbarium.tab)
                                           .setUnlocalizedName("lost_journal");
  public static final Block blockDebug = new BlockDebug()
                                         .setCreativeTab(Herbarium.tab)
                                         .setUnlocalizedName("debug");
  public static final Block blockCoalescer = new BlockCoalescer()
                                             .setCreativeTab(Herbarium.tab)
                                             .setUnlocalizedName("coalescer");
  public static final Block blockFermenter = new BlockFermenter()
                                             .setCreativeTab(Herbarium.tab)
                                             .setUnlocalizedName("fermenter");

  public static final Block blockAlstromeria = new BlockHerbariumFlower(Flowers.ALSTROMERIA.individual())
                                               .setCreativeTab(Herbarium.tab)
                                               .setUnlocalizedName("alstromeria");
  public static final Block blockBelladonna = new BlockHerbariumFlower(Flowers.BELLADONNA.individual())
                                              .setCreativeTab(Herbarium.tab)
                                              .setUnlocalizedName("belladonna");
  public static final Block blockBlueAnemone = new BlockHerbariumFlower(Flowers.BLUE_ANEMONE.individual())
                                               .setCreativeTab(Herbarium.tab)
                                               .setUnlocalizedName("blue_anemone");
  public static final Block blockBlueberryBlossom = new BlockHerbariumFlower(Flowers.BLUEBERRY_BLOSSOM.individual())
                                                    .setCreativeTab(Herbarium.tab)
                                                    .setUnlocalizedName("blueberry_blossom");
  public static final Block blockButtercup = new BlockHerbariumFlower(Flowers.BUTTERCUP.individual())
                                             .setCreativeTab(Herbarium.tab)
                                             .setUnlocalizedName("buttercup");
  public static final Block blockCavernBloom = new BlockCaveFlower(Flowers.CAVERN_BLOOM.individual())
                                               .setCreativeTab(Herbarium.tab)
                                               .setUnlocalizedName("cavern_bloom");
  public static final Block blockWinterLily = new BlockHerbariumFlower(Flowers.WINTER_LILY.individual())
                                              .setCreativeTab(Herbarium.tab)
                                              .setUnlocalizedName("winter_lily");
  public static final Block blockLancetRoot = new BlockNetherFlower(Flowers.LANCET_ROOT.individual())
                                              .setCreativeTab(Herbarium.tab)
                                              .setUnlocalizedName("lancet_root");
  public static final Block blockTailIris = new BlockHerbariumFlower(Flowers.TAIL_IRIS.individual())
                                            .setCreativeTab(Herbarium.tab)
                                            .setUnlocalizedName("tail_iris");
  public static final Block blockSpringLotus = new BlockWaterFlower(Flowers.SPRING_LOTUS.individual())
                                               .setCreativeTab(Herbarium.tab)
                                               .setUnlocalizedName("spring_lotus");
  public static final Block blockIgneousSpear = new BlockNetherFlower(Flowers.IGNEOUS_SPEAR.individual())
                                                .setCreativeTab(Herbarium.tab)
                                                .setUnlocalizedName("igneous_spear");
  public static final Block blockTropicalBerries = new BlockHerbariumFlower(Flowers.TROPICAL_BERRIES.individual())
                                                   .setCreativeTab(Herbarium.tab)
                                                   .setUnlocalizedName("tropical_berries");

  private static void register(Block block) {
    String name = block.getUnlocalizedName();
    ResourceLocation loc = new ResourceLocation("herbarium", name.substring(name.lastIndexOf('.') + 1));
    GameRegistry.register(block, loc);
    GameRegistry.register(new ItemBlock(block), loc);
  }

  public static void init() {
    register(blockCrucible);
    register(blockCoil);
    register(blockFlume);
    register(blockPipe);
    register(blockMortar);
    register(blockBarrel);
    register(blockJournal);
    register(blockDebug);
    register(blockCoalescer);
    register(blockFermenter);

    register(blockAlstromeria);
    register(blockBelladonna);
    register(blockBlueAnemone);
    register(blockBlueberryBlossom);
    register(blockButtercup);
    register(blockCavernBloom);
    register(blockWinterLily);
    register(blockLancetRoot);
    register(blockTailIris);
    register(blockSpringLotus);
    register(blockIgneousSpear);
    register(blockTropicalBerries);
  }
}