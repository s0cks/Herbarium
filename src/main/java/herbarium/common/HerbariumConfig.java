package herbarium.common;


import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public final class HerbariumConfig{
    // Ruins
    public static Property GENERATE_SPAWN_RUIN;

    // Effects
    public static Property PROWLER_VISION_RADIUS;
    public static Property LUCKY_RADIUS;

    private static Configuration CONFIG;

    public static void init(Configuration cfg){
        CONFIG = cfg;
        CONFIG.load();

        // Ruins
        GENERATE_SPAWN_RUIN = cfg.get("misc", "generateSpawnRuin", true);

        // Effects
        PROWLER_VISION_RADIUS = cfg.get("effects", "prowlerVisionRadius", 12);
        LUCKY_RADIUS = cfg.get("effects", "luckyRadius", 12);
    }

    public static void save(){
        CONFIG.save();
    }
}