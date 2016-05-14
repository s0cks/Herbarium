package herbarium.common.core;

import herbarium.api.HerbariumApi;
import herbarium.api.commentarium.IPage;
import herbarium.api.ruins.IRuin;
import herbarium.common.Herbarium;
import herbarium.common.HerbariumConfig;
import herbarium.common.items.ItemPage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class RuinGenerator {
  public static void generate(IRuin ruin, World world, BlockPos start, IPage page) {
    if (page == null) return;
    Minecraft mc = Minecraft.getMinecraft();
    int x = start.getX();
    int y = start.getY();
    int z = start.getZ();
    for (String[] level : ruin.template()) {
      for (String row : level) {
        for (char c : row.toCharArray()) {
          if (c == 'T') {
            ItemStack pageStack = new ItemStack(Herbarium.itemPage, 1);
            ItemPage.setPage(pageStack, page);
            world.setBlockState(new BlockPos(x, y, z), Blocks.CHEST.getDefaultState());
            TileEntityChest chest = ((TileEntityChest) world.getTileEntity(new BlockPos(x, y, z)));
            chest.setInventorySlotContents(0, pageStack);
          } else {
            world.setBlockState(new BlockPos(x, y, z), ruin.context()
                                                           .map(c)
                                                           .getDefaultState());
          }
          z++;
        }
        z = start.getZ();
        x++;
      }
      x = start.getX();
      y++;
    }
  }

  @SubscribeEvent
  public void onPlayerJoin(EntityJoinWorldEvent e) {
    if (e.getEntity() instanceof EntityPlayer && HerbariumConfig.GENERATE_SPAWN_RUIN.getBoolean()) {
      double x = e.getEntity().posX + e.getWorld().rand.nextInt(150);
      double z = e.getEntity().posZ + e.getWorld().rand.nextInt(150);
      BlockPos pos = e.getWorld()
                      .getTopSolidOrLiquidBlock(new BlockPos(x, 1, z));
      RuinGenerator.generate(HerbariumApi.RUIN_MANAGER.getRandom(e.getWorld().rand), e.getWorld(), pos = new BlockPos(x, pos.getY(), z), HerbariumApi.PAGE_TRACKER.unlearnedPage(((EntityPlayer) e.getEntity())));
      ((EntityPlayer) e.getEntity()).addChatComponentMessage(new TextComponentString("Spawn Ruin @" + pos));
      HerbariumConfig.GENERATE_SPAWN_RUIN.set(false);
      HerbariumConfig.save();
    }
  }
}