package herbarium.common.core.genetics;

import herbarium.api.genetics.IAllele;
import herbarium.api.genetics.IChromosome;
import herbarium.api.genetics.IMutation;
import herbarium.api.genetics.ISpecies;
import herbarium.common.Herbarium;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class Species
    implements ISpecies {
  private final Map<String, IAllele[]> templates = new HashMap<>();

  private final String uuid;

  public Species(String uuid) {
    this.uuid = uuid;
  }

  @Override
  public String uuid() {
    return this.uuid;
  }

  @Override
  public boolean isMember(ItemStack stack) {
    return false;
  }

  @Override
  public IChromosome[] templateAsChromosomes(IAllele[] template) {
    Chromosome[] chromosomes = new Chromosome[template.length];
    for(int i = 0; i < template.length; i++){
      if(template[i] != null){
        chromosomes[i] = new Chromosome(template[i]);
      }
    }
    return chromosomes;
  }

  @Override
  public void registerTemplate(String ident, IAllele[] template) {
    if (template == null) return;
    if (template.length == 0) return;
    this.templates.put(ident, template);
  }

  @Override
  public void registerMutation(IMutation mutation) {

  }

  @Override
  public IAllele[] getTemplate(String ident) {
    IAllele[] template = this.templates.get(ident);
    if(template == null) return null;
    return Arrays.copyOf(template, template.length);
  }

  @Override
  public IAllele[] randomTemplate() {
    Collection<IAllele[]> templates = this.templates.values();
    IAllele[][] array = templates.toArray(new IAllele[templates.size()][]);
    return array[Herbarium.random.nextInt(templates.size())];
  }
}