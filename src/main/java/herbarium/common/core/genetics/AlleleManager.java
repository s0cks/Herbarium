package herbarium.common.core.genetics;

import herbarium.api.genetics.IAllele;
import herbarium.api.genetics.IAlleleManager;
import herbarium.api.genetics.IChromosomeType;
import herbarium.api.genetics.ISpecies;

import java.util.LinkedHashMap;

public final class AlleleManager
implements IAlleleManager{
  private final LinkedHashMap<String, ISpecies> speciesMap = new LinkedHashMap<>();
  private final LinkedHashMap<String, IAllele> alleleMap = new LinkedHashMap<>();

  @Override
  public void registerSpecies(ISpecies species) {
    this.speciesMap.put(species.uuid(), species);
  }

  @Override
  public void registerAllele(IAllele allele, IChromosomeType type) {
    alleleMap.put(allele.uuid(), allele);
  }

  @Override
  public ISpecies getSpecies(String uuid) {
    return this.speciesMap.get(uuid);
  }

  @Override
  public IAllele getAllele(String uuid) {
    return this.alleleMap.get(uuid);
  }
}