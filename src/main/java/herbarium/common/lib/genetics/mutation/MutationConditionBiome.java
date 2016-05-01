package herbarium.common.lib.genetics.mutation;

import herbarium.api.genetics.IAllele;
import herbarium.api.genetics.IGenome;
import herbarium.api.genetics.IMutationCondition;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import scala.actors.threadpool.Arrays;

import java.util.List;

public final class MutationConditionBiome
implements IMutationCondition {
    private final List<BiomeDictionary.Type> types;

    public MutationConditionBiome(BiomeDictionary.Type... types){
        this.types = Arrays.asList(types);
    }

    @Override
    public float getChance(World world, BlockPos pos, IAllele allele0, IAllele allele1, IGenome genome0, IGenome genome1) {
        BiomeGenBase biome = world.getBiomeGenForCoords(pos);
        for(BiomeDictionary.Type type : this.types){
            if(BiomeDictionary.isBiomeOfType(biome, type)){
                return 1;
            }
        }

        return 0;
    }
}