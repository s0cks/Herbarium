package herbarium.api;

import net.minecraft.util.StatCollector;

public enum EnumTemperature{
    NONE,
    ICY,
    COLD,
    NORMAL,
    WARM,
    HOT,
    HELLISH,
    ENDER;

    public String getLocalizedName(){
        return StatCollector.translateToLocal(name().toLowerCase());
    }
}