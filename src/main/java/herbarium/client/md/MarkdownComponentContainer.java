package herbarium.client.md;

import java.awt.Dimension;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class MarkdownComponentContainer
implements IMarkdownScreen,
           Iterable<IMarkdownComponent>{
    private final Dimension size;

    private final List<IMarkdownComponent> components = new LinkedList<>();

    public MarkdownComponentContainer(int width, int height){
        this.size = new Dimension(width, height);
    }

    public int getWidth(){
        return this.size.width;
    }

    public int getHeight(){
        return this.size.height;
    }

    public int componentCount(){
        return this.components.size();
    }

    public IMarkdownComponent componentAt(int i){
        return this.components.get(i);
    }

    @Override
    public void register(IMarkdownComponent comp) {
        this.components.add(comp);
    }

    @Override
    public Iterator<IMarkdownComponent> iterator() {
        return this.components.iterator();
    }
}
