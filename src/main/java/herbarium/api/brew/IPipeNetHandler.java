package herbarium.api.brew;

import herbarium.api.WorldCoordinates;
import net.minecraft.world.World;

public interface IPipeNetHandler{
    public PipeConnection get(World world, WorldCoordinates coords);
    public int drain(World world, WorldCoordinates coords, BrewStack stack);
    public int add(World world, WorldCoordinates coords, BrewStack stack);
}