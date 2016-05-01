package herbarium.common.lib.genetics;

import herbarium.api.genetics.AlleleManager;
import herbarium.api.genetics.IClassification;
import herbarium.api.genetics.allele.IAlleleSpecies;
import net.minecraft.util.StatCollector;

import java.util.LinkedList;
import java.util.List;

public class Classification
implements IClassification{
    private final EnumClassificationLevel level;
    private final String uuid;
    private final String scientific;
    private final List<IClassification> groups = new LinkedList<>();
    private final List<IAlleleSpecies> members = new LinkedList<>();

    private IClassification parent;

    public Classification(EnumClassificationLevel level, String uuid, String scientific) {
        this.level = level;
        this.uuid = level.name().toLowerCase() + "." + uuid;
        this.scientific = scientific;
        AlleleManager.registry.registerClassification(this);
    }

    @Override
    public EnumClassificationLevel getLevel() {
        return this.level;
    }

    @Override
    public String getUUID() {
        return this.uuid;
    }

    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal("for." + this.uuid);
    }

    @Override
    public String getScientificName() {
        return this.scientific;
    }

    @Override
    public IClassification[] getMemberGroups() {
        return this.groups.toArray(new IClassification[this.groups.size()]);
    }

    @Override
    public IClassification getParent() {
        return this.parent;
    }

    @Override
    public IAlleleSpecies[] getMemberSpecies() {
        return this.members.toArray(new IAlleleSpecies[this.members.size()]);
    }

    @Override
    public void addMemberSpecies(IAlleleSpecies species) {
        this.members.add(species);
    }

    @Override
    public void addMemberGroup(IClassification group) {
        this.groups.add(group);
        group.setParent(this);
    }

    @Override
    public void setParent(IClassification parent) {
        this.parent = parent;
    }
}