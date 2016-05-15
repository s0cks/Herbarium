package herbarium.common.core.botany;

import herbarium.api.botany.EnumFlowerChromosome;
import herbarium.api.genetics.IAllele;
import herbarium.common.core.genetics.IBranchDefinition;

import java.util.Arrays;

public enum FlowerBranches
implements IBranchDefinition {
  REMEDY,
  SPIRIT,
  VENOM;

  private static IAllele[] defaultTemplate;

  private static IAllele[] defaultTemplate() {
    if (defaultTemplate == null) {
      defaultTemplate = new IAllele[EnumFlowerChromosome.values().length];
    }

    return Arrays.copyOf(defaultTemplate, defaultTemplate.length);
  }

  protected void setBranchProperties(IAllele[] template) {}

  @Override
  public final IAllele[] template() {
    IAllele[] template = defaultTemplate();
    this.setBranchProperties(template);
    return template;
  }
}