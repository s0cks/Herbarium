package herbarium.api;

import net.minecraft.util.StatCollector;

public enum EnumHumidity{
    DRY,
    NORMAL,
    DAMP;

    public String getLocalizedName(){
        return StatCollector.translateToLocal(name().toLowerCase());
    }
}
