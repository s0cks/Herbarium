package herbarium.api;

import herbarium.api.genetics.IAllele;
import herbarium.api.genetics.IClassification;

public final class Events{
    public static final class AlleleRegisteredEvent{
        public final IAllele allele;

        public AlleleRegisteredEvent(IAllele allele){
            this.allele = allele;
        }
    }

    public static final class ClassificationRegisteredEvent{
        public final IClassification branch;

        public ClassificationRegisteredEvent(IClassification branch){
            this.branch = branch;
        }
    }
}