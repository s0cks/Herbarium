package herbarium.client.md;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class MarkdownComponentContainer
implements IMarkdownScreen,
           Iterable<IMarkdownComponent>{
    private final List<IMarkdownComponent> components = new LinkedList<>();

    @Override
    public void register(IMarkdownComponent comp) {
        this.components.add(comp);
    }

    @Override
    public Iterator<IMarkdownComponent> iterator() {
        return this.components.iterator();
    }
}
