package herbarium.common;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class HerbariumSounds{
    public static SoundEvent pageFlip;

    public static void init(){
        pageFlip = register("page_flip");
    }

    private static SoundEvent register(String name){
        ResourceLocation loc = new ResourceLocation("herbarium", "sounds/" + name + ".ogg");
        return GameRegistry.register(new SoundEvent(loc).setRegistryName(loc));
    }
}