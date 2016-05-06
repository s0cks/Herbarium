package herbarium.api.brew.piping;

import herbarium.api.WorldCoordinates;
import herbarium.api.brew.BrewStack;
import net.minecraft.world.World;

public interface IPipeNetHandler{
    public PipeConnection get(World world, WorldCoordinates coords);
    public int drain(World world, WorldCoordinates coords, BrewStack stack);
    public int add(World world, WorldCoordinates coords, BrewStack stack);
}