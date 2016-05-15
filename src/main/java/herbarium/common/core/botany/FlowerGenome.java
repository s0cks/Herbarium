package herbarium.common.core.botany;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import herbarium.api.HerbariumApi;
import herbarium.api.botany.EnumFlowerChromosome;
import herbarium.api.botany.IAlleleFlowerSpecies;
import herbarium.api.botany.IFlowerGenome;
import herbarium.api.genetics.IChromosome;
import herbarium.api.genetics.ISpecies;
import herbarium.common.core.genetics.Genome;
import net.minecraft.nbt.NBTTagCompound;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public final class FlowerGenome
extends Genome
implements IFlowerGenome {
  private static final LoadingCache<NBTTagCompound, FlowerGenome> ark = CacheBuilder.newBuilder()
                                                                                    .maximumSize(128)
                                                                                    .expireAfterAccess(1, TimeUnit.MINUTES)
                                                                                    .build(new CacheLoader<NBTTagCompound, FlowerGenome>() {
                                                                                      @Override
                                                                                      public FlowerGenome load(NBTTagCompound key)
                                                                                      throws Exception {
                                                                                        return new FlowerGenome(key);
                                                                                      }
                                                                                    });

  public FlowerGenome(IChromosome[] chromosomes) {
    super(chromosomes);
  }

  public FlowerGenome(NBTTagCompound comp) {
    super(comp);
  }

  public static FlowerGenome fromNBT(final NBTTagCompound comp) {
    if (comp == null) return null;
    try {
      return ark.get(comp, new Callable<FlowerGenome>() {
        @Override
        public FlowerGenome call()
        throws Exception {
          return new FlowerGenome(comp);
        }
      });
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public IAlleleFlowerSpecies primary() {
    return ((IAlleleFlowerSpecies) this.activeAllele(EnumFlowerChromosome.SPECIES));
  }

  @Override
  public IAlleleFlowerSpecies secondary() {
    return ((IAlleleFlowerSpecies) this.inactiveAllele(EnumFlowerChromosome.SPECIES));
  }

  @Override
  public ISpecies species() {
    return HerbariumApi.FLOWER_SPECIES;
  }
}