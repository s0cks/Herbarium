package herbarium.api.ruins;

import net.minecraft.util.ResourceLocation;

public interface IRuin{
    public String uuid();
    public ResourceLocation resource();
    public String[][] template();
    public IRuinContext context();
}