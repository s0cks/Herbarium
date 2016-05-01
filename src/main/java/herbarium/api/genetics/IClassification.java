package herbarium.api.genetics;

import herbarium.api.genetics.allele.IAlleleSpecies;

public interface IClassification{
    public enum EnumClassificationLevel{
        DOMAIN,
        KINGDOM,
        PHYLUM,
        DIVISION,
        CLASS,
        ORDER,
        SUBFAMILY,
        TRIBE,
        GENUS
    }

    public EnumClassificationLevel getLevel();
    public String getUUID();
    public String getLocalizedName();
    public String getScientificName();
    public IClassification[] getMemberGroups();
    public IClassification getParent();
    public IAlleleSpecies[] getMemberSpecies();
    public void addMemberSpecies(IAlleleSpecies species);
    public void addMemberGroup(IClassification group);
    public void setParent(IClassification parent);
}