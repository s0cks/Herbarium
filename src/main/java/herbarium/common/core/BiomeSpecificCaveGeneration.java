package herbarium.common.core;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class BiomeSpecificCaveGeneration{
    private final BiomeGenBase biome;
    private final Block flower;
    private final int maxY;

    public BiomeSpecificCaveGeneration(BiomeGenBase biome, Block flower, int maxY){
        this.flower = flower;
        this.biome = biome;
        this.maxY = maxY;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onDecoration(DecorateBiomeEvent e){
        if(e.getResult() == Event.Result.ALLOW || e.getResult() == Event.Result.DEFAULT){
            for(int i = 0; i < 40; i++){
                int x = e.pos.getX() + e.rand.nextInt(16) + 8;
                int z = e.pos.getZ() + e.rand.nextInt(16) + 8;
                int y = e.rand.nextInt(this.maxY) + 4;

                BlockPos pos = new BlockPos(x, y, z);
                if(e.world.isAirBlock(pos) && this.biome.isEqualTo(e.world.getBiomeGenForCoords(pos)) && (!e.world.provider.getHasNoSky() || y < 127) && this.flower.canPlaceBlockAt(e.world, pos)){
                    e.world.setBlockState(pos, this.flower.getDefaultState());
                }
            }
        }
    }
}