package herbarium.api;

import net.minecraft.util.math.BlockPos;

public final class WorldCoordinates{
    public final BlockPos position;
    public final int dimension;

    public WorldCoordinates(BlockPos position, int dimension) {
        this.position = position;
        this.dimension = dimension;
    }

    public float getDistanceSquared(BlockPos pos){
        float x = this.position.getX() - pos.getX();
        float y = this.position.getY() - pos.getY();
        float z = this.position.getZ() - pos.getZ();
        return x * x + y * y + z * z;
    }

    public float getDistanceSquared(WorldCoordinates other){
        return getDistanceSquared(other.position);
    }
}