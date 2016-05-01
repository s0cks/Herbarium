package herbarium.common.lib.genetics;

public final class EnumAllele{
    public static enum Fertility
    implements Alleles.IAlleleValue<Integer>{
        LOW(true),
        NORMAL(true),
        HIGH;

        private final boolean dominant;

        private Fertility(boolean dominant){
            this.dominant = dominant;
        }

        private Fertility(){
            this(false);
        }

        @Override
        public Integer get(){
            return this.ordinal();
        }

        @Override
        public boolean isDominant() {
            return this.dominant;
        }
    }

    public static enum Fireproof
            implements Alleles.IAlleleValue<Boolean>{
        TRUE, FALSE;

        @Override
        public boolean isDominant(){
            return false;
        }

        @Override
        public Boolean get(){
            return this == TRUE;
        }
    }

    public static enum Lifespan
            implements Alleles.IAlleleValue<Integer> {
        SHORT(20, true),
        NORMAL(40),
        LONG(60, true);

        private final Integer value;
        private final boolean dominant;

        private Lifespan(Integer value){
            this(value, false);
        }

        private Lifespan(Integer value, boolean dominant){
            this.value = value;
            this.dominant = dominant;
        }

        @Override
        public boolean isDominant() {
            return this.dominant;
        }

        @Override
        public Integer get() {
            return this.value;
        }
    }

    public static enum Nocturnal
    implements Alleles.IAlleleValue<Boolean>{
        TRUE,
        FALSE;

        @Override
        public Boolean get() {
            return this == TRUE;
        }

        @Override
        public boolean isDominant() {
            return false;
        }
    }
}