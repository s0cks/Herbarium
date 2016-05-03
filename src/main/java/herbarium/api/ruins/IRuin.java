package herbarium.api.ruins;

import net.minecraft.util.ResourceLocation;

public interface IRuin{
    public String uuid();
    public ResourceLocation template();
    public IRuinContext context();
}