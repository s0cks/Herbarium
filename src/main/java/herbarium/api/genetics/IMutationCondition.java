package herbarium.api.genetics;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public interface IMutationCondition{
    public float getChance(World world, BlockPos pos, IAllele allele0, IAllele allele1, IGenome genome0, IGenome genome1);
}