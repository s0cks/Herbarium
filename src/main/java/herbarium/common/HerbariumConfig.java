package herbarium.common;


import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public final class HerbariumConfig{
    public static Property GENERATE_SPAWN_RUIN;

    private static Configuration CONFIG;

    public static void init(Configuration cfg){
        CONFIG = cfg;
        CONFIG.load();

        // Ruins
        GENERATE_SPAWN_RUIN = cfg.get("misc", "generateSpawnRuin", true);
    }

    public static void save(){
        CONFIG.save();
    }
}