package herbarium.common.lib.genetics;

import com.google.common.collect.HashMultimap;
import com.google.common.eventbus.EventBus;
import herbarium.api.EnumHumidity;
import herbarium.api.EnumTemperature;
import herbarium.api.Events;
import herbarium.api.flower.EnumFlowerChromosome;
import herbarium.api.genetics.AlleleManager;
import herbarium.api.genetics.IAllele;
import herbarium.api.genetics.IAlleleFactory;
import herbarium.api.genetics.IAlleleRegistry;
import herbarium.api.genetics.IAlleleSpeciesBuilder;
import herbarium.api.genetics.IChromosomeType;
import herbarium.api.genetics.IClassification;
import herbarium.api.genetics.IIndividual;
import herbarium.api.genetics.ISpecies;
import herbarium.api.genetics.allele.IAlleleArea;
import herbarium.api.genetics.allele.IAlleleBoolean;
import herbarium.api.genetics.allele.IAlleleFloat;
import herbarium.api.genetics.allele.IAlleleInt;
import herbarium.api.genetics.allele.IAlleleSpecies;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public final class Alleles{
    private static abstract class Allele
    implements IAllele {
        private final String uuid;
        private final String unlocalizedName;
        private final boolean dominant;

        protected Allele(String uuid, String unlocalizedName, boolean dominant){
            this.unlocalizedName = unlocalizedName;
            this.uuid = uuid;
            this.dominant = dominant;
        }

        @Override
        public String getUUID(){
            return this.uuid;
        }

        @Override
        public boolean isDominant(){
            return this.dominant;
        }

        @Override
        public String getLocalizedName(){
            return StatCollector.translateToLocal(this.unlocalizedName);
        }
    }

    public static abstract class AlleleSpecies
    extends Allele
    implements IAlleleSpeciesBuilder{
        private final IClassification branch;

        private boolean secret = false;
        private EnumTemperature temp = EnumTemperature.NORMAL;
        private EnumHumidity humidity = EnumHumidity.NORMAL;

        protected AlleleSpecies(String uuid, String unlocalizedName, boolean isDominant, IClassification branch){
            super(uuid, unlocalizedName, isDominant);
            this.branch = branch;
        }

        @Override
        public IAlleleSpeciesBuilder setTemperature(EnumTemperature temp){
            this.temp = temp;
            return this;
        }

        @Override
        public AlleleSpecies setHumidity(EnumHumidity humidity) {
            this.humidity = humidity;
            return this;
        }

        @Override
        public IAlleleSpeciesBuilder setIsSecret() {
            this.secret = true;
            return this;
        }
    }

    private static abstract class AlleleCategorized
    extends Allele{
        protected AlleleCategorized(String cat, String name, boolean dominant){
            super(cat + "." + name, cat + "." + name, dominant);
        }
    }

    public static final class AlleleArea
    extends AlleleCategorized
    implements IAlleleArea{
        private final int area[];

        public AlleleArea(String cat, String name, int[] value, boolean dominant){
            super(cat, name, dominant);
            this.area = value;
        }

        @Override
        public int[] get() {
            return this.area;
        }
    }

    public static final class AlleleBoolean
    extends AlleleCategorized
    implements IAlleleBoolean{
        private final boolean value;

        public AlleleBoolean(String cat, boolean value, boolean dominant){
            super(cat, Boolean.toString(value), dominant);
            this.value = value;
        }

        @Override
        public boolean get() {
            return this.value;
        }
    }

    public static final class AlleleFloat
    extends AlleleCategorized
    implements IAlleleFloat{
        private final float value;

        public AlleleFloat(String cat, String name, float value, boolean dominant){
            super(cat, name, dominant);
            this.value = value;
        }

        @Override
        public float get() {
            return this.value;
        }
    }

    public static final class AlleleInt
    extends AlleleCategorized
    implements IAlleleInt{
        private final int value;

        public AlleleInt(String cat, String name, int value, boolean dominant){
            super(cat, name, dominant);
            this.value = value;
        }

        @Override
        public int get() {
            return this.value;
        }
    }

    public static final class Factory
    implements IAlleleFactory{
        public IAlleleFloat createFloat(String cat, String name, float value, boolean dominant, IChromosomeType... types){
            AlleleFloat allele = new AlleleFloat(cat, name, value, dominant);
            AlleleManager.registry.registerAllele(allele, types);
            return allele;
        }

        @Override
        public IAlleleArea createArea(String cat, String name, int x, int y, int z, boolean isDominant, IChromosomeType... types) {
            AlleleArea allele = new AlleleArea(cat, name, new int[]{ x, y, z}, isDominant);
            AlleleManager.registry.registerAllele(allele, types);
            return allele;
        }

        @Override
        public IAlleleBoolean createBoolean(String cat, boolean value, boolean isDominant, IChromosomeType... types) {
            AlleleBoolean allele = new AlleleBoolean(cat, value, isDominant);
            AlleleManager.registry.registerAllele(allele, types);
            return allele;
        }

        @Override
        public IAlleleInt createInt(String cat, String name, int value, boolean isDominant, IChromosomeType... types) {
            AlleleInt allele = new AlleleInt(cat, name, value, isDominant);
            AlleleManager.registry.registerAllele(allele, types);
            return allele;
        }
    }

    public static interface IAlleleValue<V>{
        public V get();
        public boolean isDominant();
    }

    public static final class Registry
    implements IAlleleRegistry{
        private static final int ALLELE_ARRAY_SIZE = 2048;

        private final LinkedHashMap<String, ISpecies> species = new LinkedHashMap<>();
        private final LinkedHashMap<String, IAllele> alleleMap = new LinkedHashMap<>(ALLELE_ARRAY_SIZE);
        private final LinkedHashMap<String, IClassification> classificationMap = new LinkedHashMap<>(128);
        private final HashMultimap<IChromosomeType, IAllele> alleleByType = HashMultimap.create();
        private final HashMultimap<IAllele, IChromosomeType> typeByAllele = HashMultimap.create();
        private final EventBus bus = new EventBus();

        @Override
        public IClassification getClassification(String uuid) {
            return classificationMap.get(uuid);
        }

        @Override
        public void register(Object obj) {
            this.bus.register(obj);
        }

        @Override
        public IClassification createAndRegisterClassification(IClassification.EnumClassificationLevel level, String uuid, String scientific) {
            return new Classification(level, uuid, scientific);
        }

        @Override
        public IClassification createAndRegisterClassification(IClassification.EnumClassificationLevel level, String uuid, String scientific, IClassification... members) {
            IClassification classification = new Classification(level, uuid, scientific);
            for(IClassification member : members){
                classification.addMemberGroup(member);
            }
            return classification;
        }

        @Override
        public void registerClassification(IClassification classification) {
            if(classificationMap.containsKey(classification.getUUID())){
                throw new IllegalStateException("Already registered classification " + classification.getClass().getName());
            }

            classificationMap.put(classification.getUUID(), classification);
            this.bus.post(new Events.ClassificationRegisteredEvent(classification));
        }

        @Override
        public void registerSpecies(ISpecies species) {
            this.species.put(species.getUUID(), species);
        }

        @Override
        public void registerAllele(IAllele allele, IChromosomeType... types) {
            for(IChromosomeType type : types){
                if(!type.getAlleleClass().isAssignableFrom(allele.getClass())){
                    throw new IllegalStateException("Allele Class");
                }

                this.alleleByType.put(type, allele);
                this.typeByAllele.put(allele, type);
            }

            this.alleleMap.put(allele.getUUID(), allele);
            if(allele instanceof IAlleleSpecies){
                IClassification branch = ((IAlleleSpecies) allele).getBranch();
                if(branch != null) branch.addMemberSpecies(((IAlleleSpecies) allele));
            }

            this.bus.post(new Events.AlleleRegisteredEvent(allele));
        }

        @Override
        public IAllele getAllele(String uuid) {
            return this.alleleMap.get(uuid);
        }

        @Override
        public Collection<IAllele> getAlleles(IChromosomeType type) {
            return Collections.unmodifiableSet(this.alleleByType.get(type));
        }

        @Override
        public ISpecies getSpecies(String uuid) {
            return this.species.get(uuid);
        }

        @Override
        public ISpecies getSpecies(ItemStack stack) {
            if(stack == null) return null;

            for(ISpecies species : this.species.values()){
                if(species.isMember(stack)) return species;
            }

            return null;
        }

        @Override
        public ISpecies getSpecies(Class<? extends IIndividual> clz) {
            for(ISpecies species : this.species.values()){
                if(species.getMemberClass().isAssignableFrom(clz)) return species;
            }

            return null;
        }

        @Override
        public Map<String, ISpecies> getSpecies() {
            return Collections.unmodifiableMap(this.species);
        }

        @Override
        public Map<String, IAllele> getAlleles() {
            return Collections.unmodifiableMap(this.alleleMap);
        }

        @Override
        public Map<String, IClassification> getClassifications() {
            return Collections.unmodifiableMap(this.classificationMap);
        }

        @Override
        public boolean isIndividual(ItemStack stack) {
            return (stack != null) &&
                           (this.getSpecies(stack) != null);
        }

        @Override
        public IIndividual getIndividual(ItemStack stack) {
            ISpecies species = this.getSpecies(stack);
            return (species != null) ?
                           species.getMember(stack) :
                           null;
        }
    }

    public enum AlleleHelper{
        INSTANCE;

        public void init(){
            createAlleles(EnumAllele.Fertility.class, EnumFlowerChromosome.FERTILITY);
            createAlleles(EnumAllele.Fireproof.class, EnumFlowerChromosome.FIREPROOF);
            createAlleles(EnumAllele.Lifespan.class, EnumFlowerChromosome.LIFESPAN);
            createAlleles(EnumAllele.Nocturnal.class, EnumFlowerChromosome.NOCTURNAL);
        }

        private final Map<Class, Map<?, ? extends IAllele>> alleles = new HashMap<>();

        private static <K extends IAlleleValue<V>, V> IAllele createAllele(String cat, K value, IChromosomeType... types){
            V v = value.get();
            boolean dominant = value.isDominant();
            String name = value.toString().toLowerCase();

            Class<?> valueClass = value.getClass();
            if(Float.class.isAssignableFrom(valueClass)){
                return AlleleManager.factory.createFloat(cat, name, (Float) v, dominant, types);
            } else if(Integer.class.isAssignableFrom(valueClass)){
                return AlleleManager.factory.createInt(cat, name, (Integer) v, dominant, types);
            } else if(Boolean.class.isAssignableFrom(valueClass)){
                return AlleleManager.factory.createBoolean(cat, (Boolean) v, dominant, types);
            } else if(int[].class.isAssignableFrom(valueClass)){
                int[] area = (int[]) v;
                return AlleleManager.factory.createArea(cat, name, area[0], area[1], area[2], dominant, types);
            }

            return null;
        }

        private <K extends Enum<K> & IAlleleValue<V>, V> void createAlleles(Class<K> enumClass, IChromosomeType... types){
            String cat = enumClass.getSimpleName().toLowerCase();
            EnumMap<K, IAllele> map = new EnumMap<K, IAllele>(enumClass);
            for(K value : enumClass.getEnumConstants()){
                map.put(value, createAllele(cat, value, types));
            }
            this.alleles.put(enumClass, map);
        }

        private IAllele get(Object value){
            Class<?> valueClass = value.getClass();
            Map<?,? extends IAllele> map = this.alleles.get(valueClass);
            return map.get(value);
        }

        public <T extends Enum<T> & IChromosomeType> void set(IAllele[] alleles, T chromosomeType, IAllele allele){
            if(allele == null) return;
            alleles[chromosomeType.ordinal()] = allele;
        }

        public <T extends Enum<T> & IChromosomeType> void set(IAllele[] alleles, T chromosomeType, IAlleleValue value){
            set(alleles, chromosomeType, get(value));
        }
    }
}