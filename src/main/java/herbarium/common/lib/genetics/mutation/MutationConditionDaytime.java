package herbarium.common.lib.genetics.mutation;

import herbarium.api.genetics.IAllele;
import herbarium.api.genetics.IGenome;
import herbarium.api.genetics.IMutationCondition;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public final class MutationConditionDaytime
implements IMutationCondition{
    private final boolean daytime;

    public MutationConditionDaytime(boolean daytime){
        this.daytime = daytime;
    }

    @Override
    public float getChance(World world, BlockPos pos, IAllele allele0, IAllele allele1, IGenome genome0, IGenome genome1) {
        return world.isDaytime() == this.daytime ? 1 : 0;
    }
}