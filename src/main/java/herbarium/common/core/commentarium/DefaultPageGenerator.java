package herbarium.common.core.commentarium;

import herbarium.api.commentarium.IPage;
import herbarium.api.commentarium.IPageGenerator;
import herbarium.api.commentarium.IPageLocation;
import herbarium.api.commentarium.IPageManager;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public final class DefaultPageGenerator
implements IPageGenerator{
    private final Random rand = new Random();
    private final IPageManager manager;
    private final Queue<IPage> pages = new LinkedList<>();

    public DefaultPageGenerator(IPageManager manager){
        this.manager = manager;
        this.pages.addAll(manager.all());
        Collections.shuffle(((LinkedList<IPage>) this.pages));
    }

    @Override
    public IPageManager manager() {
        return this.manager;
    }

    private static int randBetween(Random rand, int min, int max){
        return rand.nextInt((max - min) + 1) + min;
    }

    @Override
    public IPageLocation nextLocation(World world, BlockPos last) {
        if(this.pages.isEmpty()) return null;

        int randX = randBetween(this.rand, 100, 250);
        int randZ = randBetween(this.rand, 100, 250);

        int locX = rand.nextBoolean() ?
                           randX + last.getX() :
                           randX - last.getX();
        int locZ = rand.nextBoolean() ?
                           randZ + last.getZ() :
                           randZ - last.getZ();
        BlockPos old = new BlockPos(locX, 0, locZ);
        BlockPos pos = new BlockPos(locX, world.getHeight(old).getY(), locZ);

        return new PageLocationBuilder()
                .setPage(this.pages.remove())
                .setPosition(pos)
                .newDescription()
                    .build()
                .build();
    }
}