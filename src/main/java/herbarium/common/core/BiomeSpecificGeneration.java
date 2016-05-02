package herbarium.common.core;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class BiomeSpecificGeneration{
    private final BiomeGenBase biome;
    private final Block flower;

    public BiomeSpecificGeneration(BiomeGenBase biome, Block flower){
        this.flower = flower;
        this.biome = biome;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onDecoration(DecorateBiomeEvent e){
        if(e.getResult() == Event.Result.ALLOW || e.getResult() == Event.Result.DEFAULT){
            for(int i = 0; i < 3; i++){
                if(e.rand.nextBoolean()){
                    int x = e.pos.getX() + e.rand.nextInt(16) + 8;
                    int z = e.pos.getZ() + e.rand.nextInt(16) + 8;
                    int y = e.world.getTopSolidOrLiquidBlock(e.pos).getY();

                    BlockPos pos = new BlockPos(x, y, z);
                    if(e.world.isAirBlock(pos) && this.biome.isEqualTo(e.world.getBiomeGenForCoords(pos)) && (!e.world.provider.getHasNoSky() || y < 127)){
                        e.world.setBlockState(pos, this.flower.getDefaultState());
                    }
                }
            }
        }
    }
}