package herbarium.common.core;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import herbarium.api.HerbariumApi;
import herbarium.api.commentarium.IPage;
import herbarium.api.ruins.IRuin;
import herbarium.api.ruins.IRuinContext;
import herbarium.common.Herbarium;
import herbarium.common.HerbariumConfig;
import herbarium.common.items.ItemPage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.InputStreamReader;
import java.util.Random;

public final class RuinGenerator{
    private final Random rand = new Random();

    public static void generate(IRuin ruin, World world, BlockPos start, IPage page){
        if(page == null) return;
        Minecraft mc = Herbarium.proxy.getClient();
        try(JsonReader reader = new JsonReader(new InputStreamReader(mc.getResourceManager().getResource(ruin.template()).getInputStream()))){
            JsonArray levels = new JsonParser().parse(reader).getAsJsonArray();
            IRuinContext context = ruin.context();
            int y = start.getY();
            int z = start.getZ();
            int x = start.getX();
            for(JsonElement l : levels){
                if(!l.isJsonArray()) throw new IllegalStateException("Error");
                JsonArray level = l.getAsJsonArray();
                for(JsonElement r : level){
                    String row = r.getAsString();
                    for(char c : row.toCharArray()){
                        if(c == 'T'){
                            ItemStack pageStack = new ItemStack(Herbarium.itemPage, 1);
                            ItemPage.setPage(pageStack, page);
                            world.setBlockState(new BlockPos(x, y, z), Blocks.chest.getDefaultState());
                            TileEntityChest chest = ((TileEntityChest) world.getTileEntity(new BlockPos(x, y, z)));
                            chest.setInventorySlotContents(0, pageStack);
                        } else{
                            world.setBlockState(new BlockPos(x, y, z), context.map(c).getDefaultState());
                        }
                        z++;
                    }
                    z = start.getZ();
                    x++;
                }
                x = start.getX();
                y++;
            }
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @SubscribeEvent
    public void onPlayerJoin(EntityJoinWorldEvent e){
        if(e.entity instanceof EntityPlayer && HerbariumConfig.GENERATE_SPAWN_RUIN.getBoolean()){
            double x = e.entity.posX + this.rand.nextInt(150);
            double z = e.entity.posZ + this.rand.nextInt(150);
            BlockPos pos = e.world.getTopSolidOrLiquidBlock(new BlockPos(x, 1, z));
            RuinGenerator.generate(HerbariumApi.RUIN_MANAGER.getRandom(this.rand), e.world, pos = new BlockPos(x, pos.getY(), z), HerbariumApi.PAGE_TRACKER.unlearnedPage(((EntityPlayer) e.entity)));
            ((EntityPlayer) e.entity).addChatComponentMessage(new ChatComponentText("Spawned Ruin @" + pos));
            HerbariumConfig.GENERATE_SPAWN_RUIN.set(false);
            HerbariumConfig.save();
        }
    }
}