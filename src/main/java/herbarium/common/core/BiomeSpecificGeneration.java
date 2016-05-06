package herbarium.common.core;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import scala.actors.threadpool.Arrays;

import java.util.List;

public final class BiomeSpecificGeneration{
    private final Block flower;
    private final List<BiomeGenBase> biomes;

    public BiomeSpecificGeneration(Block flower, BiomeGenBase... biomes){
        this.flower = flower;
        this.biomes = Arrays.asList(biomes);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onDecoration(DecorateBiomeEvent e){
        if(e.getResult() == Event.Result.ALLOW || e.getResult() == Event.Result.DEFAULT){
            for(int i = 0; i < 3; i++){
                int x = e.getPos().getX() + e.getRand().nextInt(16) + 8;
                int z = e.getPos().getZ() + e.getRand().nextInt(16) + 8;
                int y = e.getWorld().getTopSolidOrLiquidBlock(e.getPos()).getY();

                BlockPos pos = new BlockPos(x, y, z);
                if(e.getWorld().isAirBlock(pos) && this.validBiome(e.getWorld().getBiomeGenForCoords(pos)) && (!e.getWorld().provider.getHasNoSky() || y < 127) && this.flower.canPlaceBlockAt(e.getWorld(), pos)){
                    e.getWorld().setBlockState(pos, this.flower.getDefaultState());
                }
            }
        }
    }

    private boolean validBiome(BiomeGenBase biome){
        for(BiomeGenBase b : this.biomes){
            if(b.getRegistryType().equals(biome.getRegistryType())){
                return true;
            }
        }

        return false;
    }
}