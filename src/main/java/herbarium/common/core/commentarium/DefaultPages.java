package herbarium.common.core.commentarium;

import herbarium.api.commentarium.IPage;

public enum DefaultPages
implements IPage{
    CONTENTS,
    PROLOGUE,
    EPILOGUE;

    @Override
    public String title() {
        return this.name().substring(0, 1) + this.name().substring(1).toLowerCase();
    }

    @Override
    public String uuid() {
        return "herbarium." + this.name().toLowerCase();
    }
}